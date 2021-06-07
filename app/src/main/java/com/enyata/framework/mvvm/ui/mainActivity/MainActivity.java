package com.enyata.framework.mvvm.ui.mainActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ViewModelProviderFactory;
import com.enyata.framework.mvvm.databinding.ActivityMainBinding;
import com.enyata.framework.mvvm.ui.base.BaseActivity;
import com.enyata.framework.mvvm.ui.mainActivity.adapter.ContactAdapter;
import com.enyata.framework.mvvm.ui.mainActivity.common.LinearLayoutMangerScroller;
import com.enyata.framework.mvvm.ui.mainActivity.fragments.AddContact;
import com.enyata.framework.mvvm.ui.setting.SettingActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
        implements MainNavigator, NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, SearchView.OnQueryTextListener {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @Inject
    ViewModelProviderFactory factory;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    private MainActivityViewModel mainActivityViewModel;
    DrawerLayout drawer;
    ImageView imageView;
    ImageView plusIcon;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<ContactList> mContactLists = new ArrayList<>();
    ActivityMainBinding mActivityMainBinding;
    NavigationView navigationView;
    TextView emptySearch;
    SearchView mSearchView;
    ContactAdapter adapter;
    TextView view;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int queryLength;

    @Override
    public int getBindingVariable() {
        return com.enyata.framework.mvvm.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityViewModel getViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);

        return mainActivityViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel.setNavigator(this);
        mActivityMainBinding = getViewDataBinding();


        mSearchView = findViewById(R.id.simpleSearchView);
        mSearchView.setOnQueryTextListener(this);
        emptySearch = findViewById(R.id.emptySearch);
        mSwipeRefreshLayout = findViewById(R.id.refresh_layout);


        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            refreshList();
        });


        plusIcon = findViewById(R.id.addIcon);
        plusIcon.setOnClickListener(view -> {
            DialogFragment dialog = AddContact.newInstance();
            dialog.show(getSupportFragmentManager(), "dialog");
        });


        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutMangerScroller(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new ContactAdapter(this, mContactLists);
        mRecyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS +
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_CONTACTS))
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_CONTACTS)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            getContacts();
        }


        imageView = findViewById(R.id.home);
        imageView.setOnClickListener(this);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        view = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_contact));
        initializeCountDrawer(view);
    }

    private void initializeCountDrawer(TextView mView) {
        mView.setGravity(Gravity.CENTER_VERTICAL);
        mView.setTypeface(null, Typeface.BOLD);
        mView.setTextColor(getResources().getColor(R.color.main_color));
        mView.setText(String.valueOf(mContactLists.size()));

    }


    private void refreshList() {
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                }
                return;
            }
        }
    }

    public void getContacts() {
        ContentResolver cr = getApplicationContext().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, "upper(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ")ASC");
        while (cursor.moveToNext()) {
            int photo = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            ContactList contactList = new ContactList(id, photo, name, number, email);
            //Log.i(TAG, "main: " + name + email + number);
            mContactLists.add(contactList);
            initializeCountDrawer(view);
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_contact) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        } else {

            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<ContactList> filteredModelList = filter(mContactLists, query);
        adapter.setItems(filteredModelList);
        adapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        return false;
    }

    private List<ContactList> filter(List<ContactList> contacts, String query) {
        query = query.toLowerCase().trim();

        final List<ContactList> filteredModelList = new ArrayList<>();
        for (ContactList contact : contacts) {
            final String name = contact.getContactName().toLowerCase();
            final String number = contact.getContactNumber().toLowerCase();
            Log.i("FILTERED_QUERY", "name " + name + "/ number " + number);
            if (name.contains(query) || number.contains(query)) {
                filteredModelList.add(contact);
                emptySearch.setVisibility(View.GONE);
                //search visibility
            } else {
                emptySearch.setText("No result found");
                emptySearch.setVisibility(View.VISIBLE);
            }
        }
        return filteredModelList;
    }

}
