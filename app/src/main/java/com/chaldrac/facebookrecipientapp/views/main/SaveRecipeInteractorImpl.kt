package com.chaldrac.facebookrecipientapp.views.main

import com.chaldrac.facebookrecipientapp.entities.Recipe

class SaveRecipeInteractorImpl(var mainRepository: MainRepository) : SaveRecipeInteractor  {
    override fun execute(recipe: Recipe) {
        mainRepository.saveRecipe(recipe)
    }
}