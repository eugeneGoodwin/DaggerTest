package com.test.my.daggertest.components;

import com.test.my.daggertest.interfaces.PerApplication;
import com.test.my.daggertest.modules.MainActivityModule;
import com.test.my.daggertest.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@PerApplication
@Component(dependencies = NetComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
