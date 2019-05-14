package com.chaldrac.facebookrecipientapp.views.list

import android.widget.ListView
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent

interface StoreListeInteractor {
    fun executeUpdate(recipe: Recipe)
    fun executeDelete(recipe: Recipe)
}