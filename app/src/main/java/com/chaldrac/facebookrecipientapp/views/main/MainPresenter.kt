package com.chaldrac.facebookrecipientapp.views.main

import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.views.main.event.MainEvent
import com.chaldrac.facebookrecipientapp.views.main.ui.MainView


interface MainPresenter {
    fun onCreate()
    fun onDestroy()

    fun dismissRecipe()
    fun getNextRecipe()
    fun saveRecipe(recipe: Recipe)
    fun onEventMainThread(event: MainEvent)

    fun imageReady()
    fun imageError(error: String)

    fun getView(): MainView
}