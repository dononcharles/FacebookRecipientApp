package com.chaldrac.facebookrecipientapp.views.main

import com.chaldrac.facebookrecipientapp.entities.Recipe



interface MainRepository {
    companion object {
        val COUNT = 1
        val RECENT_SORT = "r"
        val RECIPE_RANGE = 100000
    }

    fun getNextRecipe()
    fun saveRecipe(recipe: Recipe)
    fun setRecipePage(recipePage: Int)
}