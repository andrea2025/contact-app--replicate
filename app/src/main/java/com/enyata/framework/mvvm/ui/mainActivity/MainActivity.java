package com.enyata.framework.mvvm.ui.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ViewModelProviderFactory;
import com.enyata.framework.mvvm.databinding.ActivityMainBinding;
import com.enyata.framework.mvvm.ui.add_contact.AddContactActivity;
import com.enyata.framework.mvvm.ui.base.BaseActivity;
import com.enyata.framework.mvvm.ui.mainActivity.adapter.ContactAdapter;
import com.enyata.framework.mvvm.ui.mainActivity.common.Common;
import com.enyata.framework.mvvm.ui.mainActivity.common.LinearLayoutMangerScroller;
import com.enyata.framework.mvvm.ui.setting.SettingActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> implements MainNavigator, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Inject
    ViewModelProviderFactory factory;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    private MainActivityViewModel mainActivityViewModel;
    DrawerLayout drawer;
    ImageView imageView;
    ImageView plusIcon;
    ContactAdapter mContactAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<ContactList> mContactLists = new ArrayList<>();
    ActivityMainBinding mActivityMainBinding;
    NavigationView navigationView;
    TextView contacts;

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


        plusIcon = findViewById(R.id.addIcon);
        plusIcon.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
            startActivity(intent);
        });


        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutMangerScroller(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        createPersonList();

        ContactAdapter adapter = new ContactAdapter(this, mContactLists);
        mRecyclerView.setAdapter(adapter);



        imageView = findViewById(R.id.home);
        imageView.setOnClickListener(this);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        navigationView.getMenu().getItem(0).setActionView(R.layout.contact_total);


    }


    private void createPersonList() {
        mContactLists = Common.genPeopleGroup();
        mContactLists = Common.sortList(mContactLists);//sort
        mContactLists = Common.addAlphabet(mContactLists);// add alphabet header
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.RESULT_CODE) {
            if (requestCode == Activity.RESULT_OK) {
                String group_charater_clicked = data.getStringExtra("result");
                int position = Common.findPositionWithName(group_charater_clicked, mContactLists);
                mRecyclerView.smoothScrollToPosition(position);
            }
        }


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.drawer, menu);
//
//        MenuItem menuItem = menu.findItem(R.id.nav_contact);
//        contacts = (TextView) MenuItemCompat.getActionView(menuItem);
//
//        return super.onCreateOptionsMenu(menu);
//    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_contact) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_settings) {
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
}
