package com.chaldrac.facebookrecipientapp.recipelist;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.lib.base.EventBus;
import com.chaldrac.facebookrecipientapp.views.list.ListeInteractor;
import com.chaldrac.facebookrecipientapp.views.list.ListePresenter;
import com.chaldrac.facebookrecipientapp.views.list.ListePresenterImpl;
import com.chaldrac.facebookrecipientapp.views.list.StoreListeInteractor;
import com.chaldrac.facebookrecipientapp.views.list.event.ListeEvent;
import com.chaldrac.facebookrecipientapp.views.list.ui.ListeView;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeListPresenterImpl extends BaseTest {
    @Mock
    private EventBus eventBus;
    @Mock
    private ListeView view;
    @Mock
    private ListeInteractor listeInteractor;
    @Mock
    private StoreListeInteractor storeListeInteractor;
    @Mock
    private ListeEvent listeEvent;

    private ListePresenter presenter;

    @Mock
    private Recipe recipe;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter =  new ListePresenterImpl(eventBus, view, listeInteractor, storeListeInteractor);
    }

    @Test
    public void testOnCreate_subscribedToEventBus() throws Exception {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_unsubscribedToEventBus() throws Exception {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getLView());
    }

    @Test
    public void testGetRecipes_ExecuteListInteractor() throws Exception {
        presenter.getRecipes();
        verify(listeInteractor).execute();
    }

   @Test
    public void testRemoveRecipe_ExecuteStoreInteractor() throws Exception {
        presenter.removeRecipe(recipe);
        verify(storeListeInteractor).executeDelete(recipe);
   }

    @Test
    public void testToogleFavories_True() throws Exception {
        Recipe recipe = new Recipe();
        boolean favorie = true;
        recipe.setFavorie(favorie);
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toggleFavorie(recipe);
        verify(storeListeInteractor).executeUpdate(recipeArgumentCaptor.capture());
        assertEquals(favorie, recipeArgumentCaptor.getValue().isFavorie());
    }

    @Test
    public void testToogleFavories_False() throws Exception {
        Recipe recipe = new Recipe();
        boolean favorie = false;
        recipe.setFavorie(favorie);
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toggleFavorie(recipe);
        verify(storeListeInteractor).executeUpdate(recipeArgumentCaptor.capture());
        assertEquals(favorie, recipeArgumentCaptor.getValue().isFavorie());
    }

    @Test
    public void testOnReadEvent_setRecipeOnView() throws Exception {
        when(listeEvent.getType()).thenReturn(ListeEvent.READ_EVENT);
        when(listeEvent.getRecipeList()).thenReturn(Arrays.asList(recipe));

        presenter.onEventMainThread(listeEvent);
        assertNotNull(presenter.getLView());
        verify(view).setRecipes(Arrays.asList(recipe));
    }

    @Test
    public void testOnUpdateEvent_CallUpdateOnView() throws Exception {
        when(listeEvent.getType()).thenReturn(ListeEvent.UPDATE_EVENT);
        when(listeEvent.getRecipeList()).thenReturn(Arrays.asList(recipe));

        presenter.onEventMainThread(listeEvent);
        assertNotNull(presenter.getLView());
        verify(view).recipeUpdated();
    }

    @Test
    public void testOnDeleteEvent_removesFromView() throws Exception {
        when(listeEvent.getType()).thenReturn(ListeEvent.DELETE_EVENT);
        when(listeEvent.getRecipeList()).thenReturn(Arrays.asList(recipe));

        presenter.onEventMainThread(listeEvent);
        assertNotNull(presenter.getLView());
        verify(view).recipeDeleted(recipe);
    }

    @Test
    public void testGetView_returnsView() throws Exception {
        assertEquals(view, presenter.getLView());
    }

    /* @Test
    public void testGetRecipes_ExecuteListInteractor() throws Exception {}*/

}
