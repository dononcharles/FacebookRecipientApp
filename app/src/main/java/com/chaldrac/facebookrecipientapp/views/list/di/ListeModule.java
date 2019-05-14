package com.chaldrac.facebookrecipientapp.views.list.di;

import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.lib.base.EventBus;
import com.chaldrac.facebookrecipientapp.lib.base.ImageLoader;
import com.chaldrac.facebookrecipientapp.views.list.*;
import com.chaldrac.facebookrecipientapp.views.list.adapter.ListAdapter;
import com.chaldrac.facebookrecipientapp.views.list.adapter.OnItemClickListener;
import com.chaldrac.facebookrecipientapp.views.list.ui.ListeView;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Module
public class ListeModule {
    private ListeView view;
    private OnItemClickListener clickListener;

    public ListeModule(ListeView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    ListeView providesListeView(){
        return this.view;
    }

    @Provides @Singleton
    ListePresenter providesListePresenter(EventBus eventBus, ListeView view, ListeInteractor listeInteractor, StoreListeInteractor storeListeInteractor){
        return new ListePresenterImpl(eventBus, view, listeInteractor, storeListeInteractor);
    }

    @Provides @Singleton
    ListeInteractor providesListeInteractor(ListeRepository repository){
        return new ListeInteractorImpl(repository);
    }

    @Provides @Singleton
    StoreListeInteractor providesStoreListeInteractor(ListeRepository repository){
        return new StoreListeInteractorImpl(repository);
    }

    @Provides @Singleton
    ListeRepository providesListeRepository(EventBus eventBus){
        return new ListeRepositoryImpl(eventBus);
    }

    @Provides @Singleton
    ListAdapter providesListAdapter(List<Recipe> recipeList, ImageLoader imageLoader, OnItemClickListener onItemClickListener){
        return new ListAdapter(recipeList, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.clickListener;
    }

    @Provides @Singleton
    List<Recipe> providesEmptyList(){
        return new ArrayList<Recipe>();
    }
}
