package com.zeowls.creativemindstask.dagger.Application.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zeowls.creativemindstask.dagger.Application.scope.ApplicationScope;
import com.zeowls.creativemindstask.service.ApiService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mohamed.arafa on 8/27/2017.
 */

@Module(includes = NetworkModule.class)
public class ApiServiceModule {

    @Provides
    @ApplicationScope
    public ApiService apiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScope
    public Retrofit retrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .build();
    }

    @Provides
    @ApplicationScope
    public Gson gson(){
        return new GsonBuilder().create();
    }
}
