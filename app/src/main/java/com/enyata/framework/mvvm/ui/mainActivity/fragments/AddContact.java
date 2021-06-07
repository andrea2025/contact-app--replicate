package com.enyata.framework.mvvm.ui.mainActivity.fragments;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import com.enyata.framework.mvvm.ui.view_contact.ViewContactActivity;

import java.util.ArrayList;

import static com.enyata.framework.mvvm.ui.view_contact.ViewContactActivity.TAG;

public class AddContact  extends DialogFragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    ImageView mImageView,mVert,mCamera;
    EditText edit_name, edit_number, edit_email;
    String  etContactName,etContactNumber,etContactEmail ;
    String editName,editEmail,editNumber;
    TextView save,cancel;
    public static AddContact newInstance(){
        return new  AddContact();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_contact,container,false);

        mImageView = view.findViewById(R.id.new_cancel);
        edit_name =  view.findViewById(R.id.new_name);
       edit_number = view.findViewById(R.id.new_number);
       edit_email=view.findViewById(R.id.new_email);
       save = view.findViewById(R.id.new_Save);
       mCamera = view.findViewById(R.id.cameraCreate);
       mCamera.setOnClickListener(this);

        mImageView.setOnClickListener(this);
       save.setOnClickListener(this);
        mVert = view.findViewById(R.id.vert);
        mVert.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View mView) {
        int id = mView.getId();

        switch (id){
            case R.id.new_cancel:
                dismiss();
                break;
            case R.id.vert:
                PopupMenu popupMenu = new PopupMenu(getContext(),mView);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.inflate(R.menu.main);
                popupMenu.show();
                break;
            case R.id.cameraCreate:
                selectImage(getActivity());
                break;
            case  R.id.new_Save:
                if (edit_name.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Name cannot be empty" , Toast.LENGTH_SHORT)
                            .show();

                }
                else if ((edit_number.getText().toString().isEmpty())) {
                    Toast.makeText(getActivity(), "Phone Number cannot be empty" , Toast.LENGTH_SHORT)
                            .show();
                }
                else if (edit_number.getText().toString().length() < 10 || edit_number.getText().toString().length() > 13) {
                    Toast.makeText(getActivity(), "Invalid Phone Number" , Toast.LENGTH_SHORT)
                            .show();
                }
                else if (!edit_email.getText().toString().isEmpty() && !(edit_email.getText().toString().contains("@"))) {
                    Toast.makeText(getActivity(), "Invalid Email Address" , Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                     editName = edit_name.getText().toString().trim();
                     editEmail = edit_email.getText().toString().trim();
                     editNumber = edit_number.getText().toString();
                    IntentSaveContact();
                    Intent intent = new Intent(getContext(), ViewContactActivity.class);
                    intent.putExtra("text", edit_name.getText().toString().trim());
                    intent.putExtra("number", edit_number.getText().toString());
                    getActivity().startActivity(intent);
                }
                Log.i(TAG, "onClick: hello");
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

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    private void IntentSaveContact() {
        ArrayList<ContentProviderOperation> contentProvider = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = contentProvider.size();

        contentProvider.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        contentProvider.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, edit_name.getText().toString())
                .build());


        contentProvider.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, edit_number.getText().toString())
                .build());

        contentProvider.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, etContactEmail)
                .build());


        try {
            getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProvider);
            Toast.makeText(getActivity(), "Saved contact successfully " , Toast.LENGTH_SHORT)
                    .show();

        } catch (Exception e) {
            Log.d("test", "5555");
            // Display warning
            Context ctx = getActivity();
            CharSequence txt = "contactCreationFailure";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ctx, txt, duration);
            toast.show();

            // Log exception
            Log.e("test", "Exceptoin encoutered while inserting contact: " + e);
        }
    }
    }

