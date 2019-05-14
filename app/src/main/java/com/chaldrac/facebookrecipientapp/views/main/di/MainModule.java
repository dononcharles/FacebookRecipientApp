package com.chaldrac.facebookrecipientapp.views.main.di;

import com.chaldrac.facebookrecipientapp.api.RecipeClient;
import com.chaldrac.facebookrecipientapp.api.RecipeService;
import com.chaldrac.facebookrecipientapp.lib.base.EventBus;
import com.chaldrac.facebookrecipientapp.views.main.*;
import com.chaldrac.facebookrecipientapp.views.main.ui.MainView;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class MainModule {
    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    MainView providesMainView(){
        return this.view;
    }

    @Provides @Singleton
    MainPresenter providesMainPresenter(EventBus eventBus, MainView view, SaveRecipeInteractor saveInteractor, GetNextMainInteractor getNextInteractor){
        return new MainPresenterImpl(eventBus, view, saveInteractor, getNextInteractor);
    }

    @Provides @Singleton
    SaveRecipeInteractor providesSaveRecipeInteractor(MainRepository repository){
        return new SaveRecipeInteractorImpl(repository);
    }

    @Provides @Singleton
    GetNextMainInteractor providesGetNextMainInteractor(MainRepository repository){
        return new GetNextMainInteractorImpl(repository);
    }

    @Provides @Singleton
    MainRepository providesRecipeMainRepository(EventBus eventBus, RecipeService service){
        return new MainRepositoryImpl(eventBus, service);
    }

    @Provides @Singleton
    RecipeService providesRecipeService(){
        return new RecipeClient().getRecipeService();
    }

}
