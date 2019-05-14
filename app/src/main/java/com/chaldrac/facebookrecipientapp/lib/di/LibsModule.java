package com.chaldrac.facebookrecipientapp.lib.di;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chaldrac.facebookrecipientapp.lib.GlideImageLoader;
import com.chaldrac.facebookrecipientapp.lib.GreenRobotEventBus;
import com.chaldrac.facebookrecipientapp.lib.base.EventBus;
import com.chaldrac.facebookrecipientapp.lib.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class LibsModule {

    private Activity activity;

    public LibsModule(Activity activity){
        this.activity = activity;
    }

    @Singleton
    @Provides
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){
        return new GreenRobotEventBus(eventBus);
    }

    @Singleton
    @Provides
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Singleton
    @Provides
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Singleton
    @Provides
    Activity providesActivity(){
        return this.activity;
    }

    @Singleton
    @Provides
    RequestManager providesRequestManager(){
        return Glide.with(activity);
    }
}
