package com.test.my.daggertest.components;

import com.test.my.daggertest.models.NetModel;
import com.test.my.daggertest.modules.AppModule;
import com.test.my.daggertest.modules.NetModule;
import com.test.my.daggertest.presenters.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    Retrofit retrofit();
    NetModel netModel();
    void inject(MainPresenter presenter);
}
