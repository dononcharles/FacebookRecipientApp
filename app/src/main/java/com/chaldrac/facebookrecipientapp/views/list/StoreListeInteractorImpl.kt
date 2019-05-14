package com.chaldrac.facebookrecipientapp.views.list

import android.widget.ListView
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent

class StoreListeInteractorImpl(var repository: ListeRepository) : StoreListeInteractor {

    override fun executeUpdate(recipe: Recipe) {
        repository.updateRecipes(recipe)
    }

    override fun executeDelete(recipe: Recipe) {
        repository.removeRecipes(recipe)
    }
}