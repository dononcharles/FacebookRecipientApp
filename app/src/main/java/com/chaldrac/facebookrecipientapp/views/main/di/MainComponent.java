package com.chaldrac.facebookrecipientapp.views.main.di;

import com.chaldrac.facebookrecipientapp.lib.base.ImageLoader;
import com.chaldrac.facebookrecipientapp.lib.di.LibsModule;
import com.chaldrac.facebookrecipientapp.views.main.MainPresenter;
import com.chaldrac.facebookrecipientapp.views.main.ui.MainActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {MainModule.class, LibsModule.class})
public interface MainComponent {
   // void inject(MainActivity activity);
    ImageLoader geImageLoader();
    MainPresenter gePresenter();
}
