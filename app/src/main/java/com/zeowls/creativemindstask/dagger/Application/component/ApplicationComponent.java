package com.zeowls.creativemindstask.dagger.Application.component;

import com.zeowls.creativemindstask.dagger.Application.module.ApiServiceModule;
import com.zeowls.creativemindstask.dagger.Application.scope.ApplicationScope;
import com.zeowls.creativemindstask.service.ApiService;

import dagger.Component;

/**
 * Created by mohamed.arafa on 8/27/2017.
 */

@ApplicationScope
@Component(modules = {ApiServiceModule.class})
public interface ApplicationComponent {

    ApiService getService();

}
