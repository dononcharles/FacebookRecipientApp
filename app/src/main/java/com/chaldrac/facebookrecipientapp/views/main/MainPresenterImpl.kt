package com.chaldrac.facebookrecipientapp.views.main

import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.lib.base.EventBus
import com.chaldrac.facebookrecipientapp.views.main.event.MainEvent
import com.chaldrac.facebookrecipientapp.views.main.ui.MainView
import org.greenrobot.eventbus.Subscribe
import android.os.AsyncTask.execute
import com.chaldrac.facebookrecipientapp.views.main.event.MainEvent.SAVE_EVENT
import com.chaldrac.facebookrecipientapp.views.main.event.MainEvent.NEXT_EVENT



class MainPresenterImpl(
    var eventBus: EventBus,
    var mainView: MainView?,
    var saveRecipeInteractor: SaveRecipeInteractor,
    var nextMainInteractor: GetNextMainInteractor
) : MainPresenter {
    override fun onCreate() {
        eventBus.register(this)
    }

    override fun onDestroy() {
        eventBus.unregister(this)
        mainView = null
    }

    override fun dismissRecipe() {
        if (this.mainView != null){
            mainView?.dismissAnimation()
        }
        getNextRecipe()
    }

    override fun getNextRecipe() {
        if (this.mainView != null){
            mainView?.hideUIElements()
            mainView?.showProgress()
        }
        nextMainInteractor.execute()
    }

    override fun saveRecipe(recipe: Recipe) {
        if (this.mainView != null){
            mainView?.saveAnimation()
            mainView?.hideUIElements()
            mainView?.showProgress()
        }
        saveRecipeInteractor.execute(recipe)
    }

   @Subscribe override fun onEventMainThread(event: MainEvent) {
       if (this.mainView != null) {
           val error = event.error
           if (error != null) {
               mainView?.hideProgress()
               mainView?.onGetRecipeError(error)
           } else {
               if (event.type == MainEvent.NEXT_EVENT) {
                   mainView?.setRecipe(event.recipe)
               } else if (event.type == MainEvent.SAVE_EVENT) {
                   mainView?.onRecipeSaved()
                   nextMainInteractor.execute()
               }
           }
       }
    }

    override fun imageReady() {
        if (this.mainView != null){
            mainView?.hideProgress()
            mainView?.showUIElements()
        }
    }

    override fun imageError(error: String) {
        if (this.mainView != null){
            mainView?.onGetRecipeError(error)
        }
    }

    override fun getView(): MainView {
        return this.mainView!!
    }

}