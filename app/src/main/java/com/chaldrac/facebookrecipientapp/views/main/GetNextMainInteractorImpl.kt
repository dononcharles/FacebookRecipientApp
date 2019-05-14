package com.chaldrac.facebookrecipientapp.views.main

import java.util.*


class GetNextMainInteractorImpl(var mainRepository: MainRepository) : GetNextMainInteractor {
    override fun execute() {
        val recipePage = Random().nextInt(MainRepository.RECIPE_RANGE)
        mainRepository.setRecipePage(recipePage)
        mainRepository.getNextRecipe()
    }
}