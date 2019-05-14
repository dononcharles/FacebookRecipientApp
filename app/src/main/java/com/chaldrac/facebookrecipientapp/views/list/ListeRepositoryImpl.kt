package com.chaldrac.facebookrecipientapp.views.list

import com.chaldrac.facebookrecipientapp.db.ReceipesDatabase
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.lib.base.EventBus
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent
import com.dbflow5.config.database
import com.dbflow5.query.list
import com.dbflow5.query.select
import java.util.*

class ListeRepositoryImpl(var eventBus: EventBus) : ListeRepository {

    override fun getSavedRecipes() {
        val storeRecipes = database<ReceipesDatabase>()
        (select from (Recipe::class))
            .list

        database<ReceipesDatabase>().beginTransactionAsync {
            (select from Recipe::class).list
        }
            .execute(
                success = { _, r ->
                    post(ListeEvent.READ_EVENT, r)

                }, // if successful
                error = { _, _ -> }, // any exception thrown is put here
                completion = { }) // always called success or failure
        storeRecipes.close()
    }

    override fun updateRecipes(recipe: Recipe) {
        recipe.update()
        post()
    }

    override fun removeRecipes(recipe: Recipe) {
        recipe.delete()
        post(ListeEvent.DELETE_EVENT, Arrays.asList(recipe))
    }

    private fun post(type: Int, recipeList: List<Recipe>?) {
        val event = ListeEvent(type, recipeList!!)
        eventBus.post(event)
    }

    private fun post() {
        post(ListeEvent.UPDATE_EVENT, null)
    }
}