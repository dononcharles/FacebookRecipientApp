package com.chaldrac.facebookrecipientapp.views.list.ui

import com.chaldrac.facebookrecipientapp.entities.Recipe

interface ListeView {
    fun setRecipes(data:List<Recipe>)
    fun recipeUpdated()
    fun recipeDeleted(recipe: Recipe)
}