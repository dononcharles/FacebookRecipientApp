package com.chaldrac.facebookrecipientapp;

import android.app.Application;
import android.content.Intent;
import com.chaldrac.facebookrecipientapp.db.ReceipesDatabase;
import com.chaldrac.facebookrecipientapp.lib.di.LibsModule;
import com.chaldrac.facebookrecipientapp.views.list.adapter.OnItemClickListener;
import com.chaldrac.facebookrecipientapp.views.list.di.DaggerListeComponent;
import com.chaldrac.facebookrecipientapp.views.list.di.ListeComponent;
import com.chaldrac.facebookrecipientapp.views.list.di.ListeModule;
import com.chaldrac.facebookrecipientapp.views.list.ui.ListeActivity;
import com.chaldrac.facebookrecipientapp.views.list.ui.ListeView;
import com.chaldrac.facebookrecipientapp.views.login.ui.LoginActivity;
import com.chaldrac.facebookrecipientapp.views.main.di.DaggerMainComponent;
import com.chaldrac.facebookrecipientapp.views.main.di.MainComponent;
import com.chaldrac.facebookrecipientapp.views.main.di.MainModule;
import com.chaldrac.facebookrecipientapp.views.main.ui.MainActivity;
import com.chaldrac.facebookrecipientapp.views.main.ui.MainView;
import com.dbflow5.config.DatabaseConfig;
import com.dbflow5.config.FlowConfig;
import com.dbflow5.config.FlowManager;
import com.dbflow5.database.AndroidSQLiteOpenHelper;
import com.facebook.login.LoginManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FacebookRecipientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
       /* Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);*/
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBTearDown();
    }

    private void DBTearDown() {
        FlowManager.destroy();
    }

    private void initDB() {
        //  FlowManager.init(this);
        FlowManager.init(new FlowConfig.Builder(this)
                .database(DatabaseConfig.builder(ReceipesDatabase.class, AndroidSQLiteOpenHelper.createHelperCreator(this))
                        .databaseName("Recipes")
                        .build())
                .build());
    }

    public void logout() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public MainComponent getRecipeMainComponent(MainActivity activity, MainView view) {
        return DaggerMainComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .mainModule(new MainModule(view))
                .build();
    }

    public ListeComponent getListeComponent(ListeActivity activity, ListeView view, OnItemClickListener listener) {
        return DaggerListeComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .listeModule(new ListeModule(view, listener))
                .build();
    }
}
