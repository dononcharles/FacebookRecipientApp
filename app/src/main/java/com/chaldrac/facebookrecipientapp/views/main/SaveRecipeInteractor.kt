package com.chaldrac.facebookrecipientapp.views.main

import com.chaldrac.facebookrecipientapp.entities.Recipe

interface SaveRecipeInteractor {
    fun execute(recipe: Recipe)
}