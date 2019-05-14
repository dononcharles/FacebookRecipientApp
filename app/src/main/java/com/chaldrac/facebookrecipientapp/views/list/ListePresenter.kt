package com.chaldrac.facebookrecipientapp.views.list

import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent
import com.chaldrac.facebookrecipientapp.views.list.ui.ListeView

interface ListePresenter {
    fun onCreate()
    fun onDestroy()
    fun getRecipes()
    fun removeRecipe(recipe: Recipe)
    fun toggleFavorie(recipe: Recipe)

    fun onEventMainThread(event : ListeEvent)
    fun getLView() : ListeView?
}