package com.enyata.framework.mvvm.ui.add_contact;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ViewModelProviderFactory;
import com.enyata.framework.mvvm.databinding.ActivityAddContactBinding;
import com.enyata.framework.mvvm.ui.base.BaseActivity;
import com.enyata.framework.mvvm.ui.mainActivity.MainActivity;

import javax.inject.Inject;

public class AddContactActivity extends BaseActivity<ActivityAddContactBinding,AddContactViewModel> implements AddContactNavigator {
@Inject
    ViewModelProviderFactory factory;
AddContactViewModel mAddContactViewModel;
ActivityAddContactBinding mActivityAddContactBinding;
ImageView mCameraClick, mCancelAddContact;
    @Override
    public int getBindingVariable() {
        return com.enyata.framework.mvvm.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public AddContactViewModel getViewModel() {
        mAddContactViewModel = ViewModelProviders.of(this,factory).get(AddContactViewModel.class);
        return mAddContactViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddContactViewModel.setNavigator(this);
       mActivityAddContactBinding = getViewDataBinding();

       mCameraClick = mActivityAddContactBinding.camera;
       mCameraClick.setOnClickListener(view -> selectImage(AddContactActivity.this));

       mCancelAddContact = mActivityAddContactBinding.cancel;
       mCancelAddContact.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           startActivity(intent);
       });


    }

    private  void  selectImage(Context context){
        final CharSequence[] options = {"Take photo", "Choose photo","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Change photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose photo")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setType("image/*");
                    String[] mimeTypes = {"image/jpeg", "image/png"};
                    pickPhoto.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){


            if(requestCode == 0){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                mCameraClick.setImageBitmap(bmp);

            }else if(requestCode == 1){

                Uri selectedImageUri = data.getData();
                mCameraClick.setImageURI(selectedImageUri);
            }

        }
    }
}

