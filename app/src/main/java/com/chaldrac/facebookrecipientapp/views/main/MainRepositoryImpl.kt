package com.chaldrac.facebookrecipientapp.views.main

import com.chaldrac.facebookrecipientapp.api.RecipeService
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.lib.base.EventBus
import com.chaldrac.facebookrecipientapp.views.main.event.MainEvent
import com.chaldrac.facebookrecipientapp.api.RecipeSearchResponse
import com.chaldrac.facebookrecipientapp.BuildConfig
import com.chaldrac.facebookrecipientapp.views.main.MainRepository.Companion.COUNT
import com.chaldrac.facebookrecipientapp.views.main.MainRepository.Companion.RECENT_SORT
import com.chaldrac.facebookrecipientapp.views.main.MainRepository.Companion.RECIPE_RANGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainRepositoryImpl(var eventBus : EventBus, var service: RecipeService) : MainRepository {

    private var recipePage : Int = 0

    override fun getNextRecipe() {
        val call = service.search(BuildConfig.FOOD_API_KEY, RECENT_SORT, COUNT, recipePage)
        val callback = object : Callback<RecipeSearchResponse> {
           override fun onResponse(call: Call<RecipeSearchResponse>, response: Response<RecipeSearchResponse>) {
                if (response.isSuccessful) {
                    val recipeSearchResponse = response.body()
                    if (recipeSearchResponse?.count == 0) {
                        setRecipePage(Random().nextInt(RECIPE_RANGE))
                        getNextRecipe()
                    } else {
                        val recipe = recipeSearchResponse?.firstRecipe
                        if (recipe != null) {
                            post(recipe)
                        } else {
                            post(response.message())
                        }
                    }
                } else {
                    post(response.message())
                }
            }

            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                post(t.localizedMessage)
            }
        }

        call.enqueue(callback)
    }

    override fun saveRecipe(recipe: Recipe) {
        recipe.save()
        post()
    }

    override fun setRecipePage(recipePage: Int) {
        this.recipePage = recipePage
    }

    private fun post(error: String?, type: Int, recipe: Recipe?) {
        val event = MainEvent()
        event.type = type
        event.error = error
        event.recipe = recipe
        eventBus.post(event)
    }

    private fun post(recipe: Recipe) {
        post(null, MainEvent.NEXT_EVENT, recipe)
    }

    private fun post(error: String) {
        post(error, MainEvent.NEXT_EVENT, null)
    }

    private fun post() {
        post(null, MainEvent.SAVE_EVENT, null)
    }

}