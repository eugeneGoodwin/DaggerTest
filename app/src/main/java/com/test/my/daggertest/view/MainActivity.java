package com.test.my.daggertest.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.test.my.daggertest.Application;
import com.test.my.daggertest.R;
import com.test.my.daggertest.components.DaggerMainActivityComponent;
import com.test.my.daggertest.databinding.ActivityMainBinding;
import com.test.my.daggertest.modules.MainActivityModule;
import com.test.my.daggertest.presenters.MainPresenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainPresenter.View{

    @Inject
    MainPresenter mPresenter;

    private ActivityMainBinding mBinding;

    @Override
    public RecyclerView getRecyclerView(){
        return mBinding.recyclerView;
    }

    @Override
    public TextView getStatus(){
        return mBinding.textView;
    }

    @Override
    public Context getContext() {
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        DaggerMainActivityComponent.builder()
                .netComponent(((Application)getApplication()).getNetComponent())
                .mainActivityModule(new MainActivityModule())
                .build().inject(this);

        RxView.clicks(mBinding.postButton).subscribe(v -> mPresenter.start());

        mPresenter.attachView(this);
        mPresenter.init();
    }
}
