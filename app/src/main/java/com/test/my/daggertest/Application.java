package com.test.my.daggertest;

import com.test.my.daggertest.components.DaggerNetComponent;
import com.test.my.daggertest.components.NetComponent;
import com.test.my.daggertest.modules.AppModule;
import com.test.my.daggertest.modules.NetModule;

public class Application extends android.app.Application {
    static Application mApplication;

    public static final String SERVER_ENDPOINT = "https://jsonplaceholder.typicode.com/";

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(SERVER_ENDPOINT))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public static Application getApplication(){ return mApplication; }
}
