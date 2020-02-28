package com.enyata.framework.mvvm.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ViewModelProviderFactory;
import com.enyata.framework.mvvm.databinding.ActivityMainBinding;
import com.enyata.framework.mvvm.ui.base.BaseActivity;


import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> implements MainNavigator {

    @Inject
    ViewModelProviderFactory factory;
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


private MainActivityViewModel mainActivityViewModel;

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
    }


}
