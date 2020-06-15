package com.enyata.framework.mvvm.ui.view_contact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ui.edit_contact.EditContactActivity;

import text.drawable.mylibrary.TextDrawable;
import text.drawable.mylibrary.util.ColorGenerator;

public class ViewContactActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mImageView;
    TextView mTextView;
    LinearLayout mEditContact;
    Bitmap picture;

    public static final String TAG = "ViewContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        mImageView = findViewById(R.id.imgName);
        mTextView = findViewById(R.id.contactName);
        mEditContact = findViewById(R.id.editContact);

        mEditContact.setOnClickListener(this);


        getIncomingIntent();


    }

    private void getIncomingIntent() {

        String contactName2 = getIntent().getStringExtra("text");
        // String imageColor = getIntent().getStringExtra("image");

        ColorGenerator mColor = ColorGenerator.MATERIAL;
        TextDrawable drawable2 = TextDrawable.builder().buildRound(String.valueOf(contactName2.charAt(0)), mColor.getRandomColor());

        mImageView.setImageDrawable(drawable2);
        mTextView.setText(contactName2);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), EditContactActivity.class);
        startActivity(intent);
    }
}
