package com.chaldrac.facebookrecipientapp.views.list

import android.widget.ListView
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.lib.base.EventBus
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent
import com.chaldrac.facebookrecipientapp.views.list.ui.ListeView

class ListePresenterImpl(var eventBus: EventBus, var view:ListeView?, var listeInteractor: ListeInteractor, var storeListeInteractor: StoreListeInteractor) : ListePresenter{
    override fun onCreate() {
eventBus.register(this)
    }

    override fun onDestroy() {
        eventBus.unregister(this)
        view = null
    }

    override fun getRecipes() {
        listeInteractor.execute()
    }

    override fun removeRecipe(recipe: Recipe) {
        storeListeInteractor.executeDelete(recipe)
    }

    override fun toggleFavorie(recipe: Recipe) {
        storeListeInteractor.executeUpdate(recipe)
    }

    override fun onEventMainThread(event: ListeEvent) {
        if (this.view != null){
            when(event.type){
                ListeEvent.READ_EVENT ->
                    view?.setRecipes(event.recipeList)

                ListeEvent.UPDATE_EVENT ->
                    view?.recipeUpdated()

                ListeEvent.DELETE_EVENT -> {
                    val recipe = event.recipeList[0]
                    view?.recipeDeleted(recipe)
                }
            }
        }
    }

    override fun getLView(): ListeView? {
        return this.view
    }

}