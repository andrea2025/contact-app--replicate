package com.enyata.framework.mvvm.ui.view_contact;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ui.mainActivity.MainActivity;

import java.util.ArrayList;

import text.drawable.mylibrary.TextDrawable;
import text.drawable.mylibrary.util.ColorGenerator;

public class ViewContactActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {
    ImageView mImageView,mViewVert,mArrow;
    TextView mTextName, mContactNumber,mContactEmail;
    LinearLayout mEditContact,mMessage,mCall;
    String contactName2,contactNumber,contactEmail;
    String newName,newNumber;


    public static final String TAG = "ViewContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        mImageView = findViewById(R.id.imgName);
        mTextName = findViewById(R.id.contactName);
        mEditContact = findViewById(R.id.editContact);
        mViewVert = findViewById(R.id.viewVert);
        mArrow = findViewById(R.id.viewArrow);
        mContactNumber= findViewById(R.id.ContactNumber);
        mMessage = findViewById(R.id.message);
        mCall = findViewById(R.id.call);
       // mContactEmail = findViewById(R.id.emailAddress);


        mArrow.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        mViewVert.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this,view);
            popupMenu.setOnMenuItemClickListener(ViewContactActivity.this);
            popupMenu.inflate(R.menu.view_menu);
            popupMenu.show();
        });

        mEditContact.setOnClickListener(view -> {
            openDialog();
        });

        getIncomingIntent();

        mMessage.setOnClickListener(this);
        mCall.setOnClickListener(this);


    }


    private void openDialog() {
        DialogFragment dialog = EditDialog.newInstance();
        dialog.show(getSupportFragmentManager(),"tag");
        Bundle b = new Bundle();
        b.putString("text", mTextName.getText().toString());
        b.putString("number",mContactNumber.getText().toString());
        //b.putString("email",mContactEmail.getText().toString());
        dialog.setArguments(b);
    }



    private void getIncomingIntent() {
         contactName2 = getIntent().getStringExtra("text");
         contactNumber = getIntent().getStringExtra("number");
        //contactEmail = getIntent().getStringExtra("email");
        ColorGenerator mColor = ColorGenerator.MATERIAL;
        TextDrawable drawable2 = TextDrawable.builder().buildRound(String.valueOf(contactName2.charAt(0)),
                mColor.getColor(contactName2));
        mImageView.setImageDrawable(drawable2);
        mTextName.setText(contactName2);
        mContactNumber.setText(contactNumber);
        //mContactEmail.setText(contactEmail);
    }

    public  boolean deleteContactList(Context context, String name){
        ContentResolver cr = context.getContentResolver();
        String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
        String[] params = new String[] {name};

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(where, params)
                .build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
      return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.delete:
                new AlertDialog.Builder(this,R.style.AlertDialogCustom)
                        .setTitle("Delete this contact")
                        .setCancelable(false)
                        .setMessage("Name will be removed from contacts")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            deleteContactList(getApplicationContext(),contactName2);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {

                        })
                        .show();
            return true;
            default:
                return false;
        }

    }


    @Override
    public void onClick(View view) {
        int Id = view.getId();
        switch (Id) {
            case R.id.message:
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + contactName2));
            startActivity(intent);
            break;
          case  R.id.call:
              Intent callIntent = new Intent(Intent.ACTION_DIAL);
              callIntent.setData(Uri.parse("tel:" + contactNumber));
              startActivity(callIntent);
              break;

        }
    }
}
