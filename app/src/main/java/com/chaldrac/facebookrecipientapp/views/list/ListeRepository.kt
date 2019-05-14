package com.chaldrac.facebookrecipientapp.views.list

import com.chaldrac.facebookrecipientapp.entities.Recipe

interface ListeRepository {
    fun getSavedRecipes()
    fun updateRecipes(recipe: Recipe)
    fun removeRecipes(recipe: Recipe)
}