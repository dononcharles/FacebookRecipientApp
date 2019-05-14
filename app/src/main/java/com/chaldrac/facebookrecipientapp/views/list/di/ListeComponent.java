package com.chaldrac.facebookrecipientapp.views.list.di;

import com.chaldrac.facebookrecipientapp.lib.di.LibsModule;
import com.chaldrac.facebookrecipientapp.views.list.ListePresenter;
import com.chaldrac.facebookrecipientapp.views.list.adapter.ListAdapter;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ListeModule.class, LibsModule.class})
public interface ListeComponent {
    ListAdapter getAdapter();
    ListePresenter getPresenter();
}
