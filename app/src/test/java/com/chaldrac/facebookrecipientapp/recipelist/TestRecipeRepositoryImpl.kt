package com.chaldrac.facebookrecipientapp.recipelist

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.chaldrac.facebookrecipientapp.BaseTest
import com.chaldrac.facebookrecipientapp.FacebookRecipientApplication
import com.chaldrac.facebookrecipientapp.db.ReceipesDatabase
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.lib.base.EventBus
import com.chaldrac.facebookrecipientapp.views.list.ListeRepository
import com.chaldrac.facebookrecipientapp.views.list.ListeRepositoryImpl
import com.chaldrac.facebookrecipientapp.views.list.StoreListeInteractorImpl
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent
import com.dbflow5.annotation.Database
import com.dbflow5.config.database
import com.dbflow5.config.databaseForTable
import com.dbflow5.database.DatabaseWrapper
import com.dbflow5.query.list
import com.dbflow5.query.select
import com.dbflow5.structure.insert
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.robolectric.RuntimeEnvironment

import java.util.ArrayList

import org.mockito.Mockito.verify

class TestRecipeRepositoryImpl : BaseTest() {
    @Mock
    private val eventBus: EventBus? = null
    @Mock
    private val onCreate:Application?=null

    private var listeRepository: ListeRepository? = null

    private var app: FacebookRecipientApplication? = null

    private var listeEventArgumentCaptor: ArgumentCaptor<ListeEvent>? = null

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        listeRepository = ListeRepositoryImpl(eventBus!!)
        app = FacebookRecipientApplication()
        listeEventArgumentCaptor = ArgumentCaptor.forClass(ListeEvent::class.java)

      /// app!!.onCreate()
      //  app!!.onCreate() = onCreate?.onCreate()!!
    }

    @Test
    @Throws(Exception::class)
    fun testgetSaveRecipes_eventPosted() {
        val recipesToStore = 5
        var currentRecipe: Recipe
        val recipes = ArrayList<Recipe>()
        for (i in 0 until recipesToStore) {
            val currentRecipe = Recipe()
            val current = databaseForTable<Recipe>()
            currentRecipe.recipeId = "id$i"
            currentRecipe.save()
            currentRecipe.insert(current)
            recipes.add(currentRecipe)
        }
        var recFromDb = ArrayList<Recipe>()
        database<ReceipesDatabase>()
            .beginTransactionAsync {
                (select from Recipe::class).list
            }
            .execute(
                success = { _, r ->
                    recFromDb = r as ArrayList<Recipe>
                }, // if successful
                error = { _, _ -> }, // any exception thrown is put here
                completion = { }) // always called success or failure

        listeRepository?.getSavedRecipes()

        verify(eventBus)?.post(listeEventArgumentCaptor?.capture())
        val event = listeEventArgumentCaptor?.value

        assertEquals(ListeEvent.READ_EVENT, event?.type)
        assertEquals(recFromDb, event?.recipeList)


        for (recipe in recipes) {
            recipe.delete()
        }
    }

    /* @Test
     @Throws(Exception::class)
     fun testExecuteDelete_CallRepo() {
         interactor.executeDelete(recipe)
         verify<ListeRepository>(listeRepository).removeRecipes(recipe)
     }*/
}
