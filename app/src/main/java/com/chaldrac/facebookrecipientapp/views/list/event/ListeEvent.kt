package com.chaldrac.facebookrecipientapp.views.list.event

import com.chaldrac.facebookrecipientapp.entities.Recipe

open class ListeEvent(open var type: Int, open var recipeList: List<Recipe>) {

    companion object {
        const val READ_EVENT = 0
        const val UPDATE_EVENT = 1
        const val DELETE_EVENT = 2
    }

}
