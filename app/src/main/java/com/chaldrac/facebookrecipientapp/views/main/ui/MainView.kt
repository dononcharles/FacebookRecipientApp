package com.chaldrac.facebookrecipientapp.views.main.ui

import com.chaldrac.facebookrecipientapp.entities.Recipe




interface MainView  {
    fun showProgress()
    fun hideProgress()
    fun showUIElements()
    fun hideUIElements()
    fun saveAnimation()
    fun dismissAnimation()

    fun onRecipeSaved()

    fun setRecipe(recipe: Recipe)
    fun onGetRecipeError(error: String)

}
