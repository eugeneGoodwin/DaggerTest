package com.test.my.daggertest.modules;

import com.test.my.daggertest.interfaces.PerApplication;
import com.test.my.daggertest.models.NetModel;
import com.test.my.daggertest.presenters.BasePresenter;
import com.test.my.daggertest.presenters.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    @PerApplication
    MainPresenter provideMainPresenter(){
        return new MainPresenter();
    }
}
