package com.zeowls.creativemindstask;

import android.app.Application;
import android.content.Context;
import com.zeowls.creativemindstask.dagger.Application.component.ApplicationComponent;
import com.zeowls.creativemindstask.dagger.Application.component.DaggerApplicationComponent;
import com.zeowls.creativemindstask.dagger.Application.module.ContextModule;
import com.zeowls.creativemindstask.service.ApiService;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/*
 * Created by mohamed.arafa on 8/27/2017.
 */

public class MyApplication extends Application {

    private ApiService mApiService;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        mApiService = applicationComponent.getService();
    }

    public ApiService getApiService(){
        return mApiService;
    }

}
