package com.enyata.framework.mvvm.ui.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.enyata.framework.mvvm.R;

public class SettingActivity extends AppCompatActivity {
TextView mSort,mFormat,mDefault,mExport,mImport,mUndo,mBlock,mRestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSort = findViewById(R.id.sort);
        mSort.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            String[] sortName = getApplicationContext().getResources().getStringArray(R.array.sort_values);

            builder.setTitle("Sort by");
            builder.setSingleChoiceItems(sortName, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
        });

        mFormat = findViewById(R.id.format);
        mFormat.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            String[] sortName = getApplicationContext().getResources().getStringArray(R.array.format_values);

            builder.setTitle("Name format");
            builder.setSingleChoiceItems(sortName, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
        });

        mDefault = findViewById(R.id.defaultAcct);
        mDefault.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            String[] sortName = getApplicationContext().getResources().getStringArray(R.array.format_values);

            builder.setTitle("Choose account");
            //builder.setIcon(R.drawable.google_contacts_logo);
            builder.setSingleChoiceItems(sortName, -1, (dialogInterface, i) -> {

            }).setNegativeButton("Cancel", (dialogInterface, i) -> {

            });
            AlertDialog mDialog = builder.create();
            mDialog.show();

        });

        mImport = findViewById(R.id.textImport);
        mImport.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            String[] sortName = getApplicationContext().getResources().getStringArray(R.array.import_values);

            builder.setTitle("Import contact from");
            builder.setSingleChoiceItems(sortName, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
        });

        mExport = findViewById(R.id.textExport);
        mExport.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            String[] sortName = getApplicationContext().getResources().getStringArray(R.array.export_values);

            builder.setTitle("Choose Account");
            boolean[] checkedItems = {false, false};
            builder.setMultiChoiceItems(sortName, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                }
            }).setPositiveButton("Export to vcf file", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
        });

        mRestore = findViewById(R.id.textRestore);
        mRestore.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            String[] sortName = getApplicationContext().getResources().getStringArray(R.array.format_values);

            builder.setTitle("Choose account");
            //builder.setIcon(R.drawable.google_contacts_logo);
            builder.setSingleChoiceItems(sortName, -1, (dialogInterface, i) -> {

            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
        });

        mUndo = findViewById(R.id.textUndo);
        mUndo.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            String[] sortName = getApplicationContext().getResources().getStringArray(R.array.format_values);

            builder.setTitle("Choose account");
            //builder.setIcon(R.drawable.google_contacts_logo);
            builder.setSingleChoiceItems(sortName, -1, (dialogInterface, i) -> {

            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
        });

    }



}
