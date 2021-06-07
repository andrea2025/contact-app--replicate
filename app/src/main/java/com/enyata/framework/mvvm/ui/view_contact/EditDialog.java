package com.enyata.framework.mvvm.ui.view_contact;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ui.mainActivity.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class EditDialog extends DialogFragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    ImageView mCamera,mImageView, mVert;
     EditText edit_name,edit_phone,edit_email;
    String name, number, email,contactEmail;
    String editName, editNumber, editEmail;
    TextView mSave;


    public static EditDialog newInstance() {
        return new EditDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_contact, container, false);
        edit_name = view.findViewById(R.id.firstName);
        edit_phone = view.findViewById(R.id.phoneNumber);
        edit_email = view.findViewById(R.id.emailAddress);
        mCamera = view.findViewById(R.id.camera);
        mCamera.setOnClickListener(this);
        mSave = view.findViewById(R.id.editSave);
        mSave.setOnClickListener(this);


        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("text");
            number = bundle.getString("number");
            email = bundle.getString("email");

        }

        edit_name.setText(name);
        edit_phone.setText(number);
        edit_email.setText(email);



        editName = edit_name.getText().toString().trim();
        editEmail = edit_email.getText().toString().trim();
        editNumber = edit_phone.getText().toString();



        mImageView = view.findViewById(R.id.editCancel);
        mImageView.setOnClickListener(this);
        mVert = view.findViewById(R.id.editVert);
        mVert.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View mView) {
        int id = mView.getId();


        switch (id) {
            case R.id.editCancel:
                dismiss();
                break;
            case R.id.editVert:
                PopupMenu popupMenu = new PopupMenu(getContext(), mView);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.inflate(R.menu.edit_menu);
                popupMenu.show();
                break;
            case R.id.camera:
                selectImage(getActivity());
                break;

            case  R.id.editSave:
                if (edit_name.getText().toString().isEmpty()) {
                    Snackbar.make(mView, "Name cannot be empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else if ((edit_phone.getText().toString().isEmpty())) {
                    Snackbar.make(mView, "Phone Number cannot be empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else if (edit_phone.getText().toString().length() < 10 || edit_phone.getText().toString().length() > 13) {
                    Snackbar.make(mView, "Invalid Phone Number", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else if (!edit_email.getText().toString().isEmpty() && !(edit_email.getText().toString().contains("@"))) {
                    Snackbar.make(mView, "Invalid Email Address", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    updateContactList(edit_name.getText().toString().trim(),edit_phone.getText().toString(),
                            ContactsContract.Contacts.Data.RAW_CONTACT_ID);

                    Intent intent = new Intent(getContext(),ViewContactActivity.class);
                   intent.putExtra("text", edit_name.getText().toString().trim());
                  intent.putExtra("number", edit_phone.getText().toString());
                    getActivity().startActivity(intent);


                }
        }
    }

    public boolean updateContactList(String name, String number,String ContactId){

        String where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND "
                + ContactsContract.Data.MIMETYPE + " = ?";

        String[] nameParams = new String[]{ContactId,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
        String[] numberParams = new String[]{ContactId,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        if(!name.equals("")) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(where,nameParams)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                    .build());
        }
        if(!number.equals("")) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(where, numberParams)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                    .build());
        }
        try {
            getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(getActivity(), "Update contact successfully" + name  + number , Toast.LENGTH_SHORT)
                    .show();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public  boolean deleteContactList(Context context,String name){
        ContentResolver cr = context.getContentResolver();
        String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
        String[] params = new String[] {name};

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(where, params)
                .build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(getActivity(), "Delete contact successfully" + name  + number , Toast.LENGTH_SHORT)
                    .show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.editDelete:
                new android.app.AlertDialog.Builder(getActivity(),R.style.AlertDialogCustom)
                        .setTitle("Delete this contact")
                        .setCancelable(false)
                        .setMessage("Name will be removed from contacts")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            deleteContactList(getActivity(),name);
                            Intent intent = new Intent(getContext(), MainActivity.class);
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

    private void selectImage(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        final CharSequence[] options = {"Camera", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Camera")) {
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                pickPhoto.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(pickPhoto, 1);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){
            if(requestCode == 0){
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                mCamera.setImageBitmap(bmp);
            }else if(requestCode== 1){
                Uri selectedImageUri = data.getData();
                mCamera.setImageURI(selectedImageUri);
            }

        }
    }



}

