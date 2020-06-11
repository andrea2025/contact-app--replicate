package com.enyata.framework.mvvm.ui.mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ui.mainActivity.adapter.ContactAlphabetAdapter;

public class AlphabetActivity extends AppCompatActivity {

    RecyclerView mRecyclerAlphabet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        mRecyclerAlphabet = findViewById(R.id.recycler_alphabet);
        ContactAlphabetAdapter contactAlphabetAdapter = new ContactAlphabetAdapter();
        contactAlphabetAdapter.setOnAlphabetClick(new OnAlphabetClick() {
            @Override
            public void OnAlphabetClickListener(String alphabet, int mPosition) {
                if (mPosition != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("result", alphabet);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        mRecyclerAlphabet.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerAlphabet.setAdapter(contactAlphabetAdapter);
    }
}
