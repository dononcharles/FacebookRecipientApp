package com.chaldrac.facebookrecipientapp.views.list

import android.widget.ListView
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent

class ListeInteractorImpl(var repository: ListeRepository) : ListeInteractor{

    override fun execute() {
        repository.getSavedRecipes()
    }
}