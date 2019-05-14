package com.chaldrac.facebookrecipientapp.recipemain;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.lib.base.EventBus;
import com.chaldrac.facebookrecipientapp.views.main.GetNextMainInteractor;
import com.chaldrac.facebookrecipientapp.views.main.MainPresenterImpl;
import com.chaldrac.facebookrecipientapp.views.main.SaveRecipeInteractor;
import com.chaldrac.facebookrecipientapp.views.main.event.MainEvent;
import com.chaldrac.facebookrecipientapp.views.main.ui.MainView;
import kotlin.jvm.Throws;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeMainPresenterImplTest extends BaseTest {
    @Mock
    private EventBus eventBus;
    @Mock
    private MainView mainView;
    @Mock
    private SaveRecipeInteractor saveRecipeInteractor;
    @Mock
    private GetNextMainInteractor nextMainInteractor;
    @Mock
    Recipe recipe;
    @Mock
    MainEvent mainEvent;

    private MainPresenterImpl presenter;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new MainPresenterImpl(eventBus, mainView, saveRecipeInteractor, nextMainInteractor);
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
        assertNull(presenter.getMainView());
    }

    @Test
    public void saveRecipeTest_executeSaveInteractor() throws Exception {
        presenter.saveRecipe(recipe);
        assertNotNull(presenter.getMainView());
        verify(mainView).saveAnimation();
        verify(mainView).hideUIElements();
        verify(mainView).showProgress();
        verify(saveRecipeInteractor).execute(recipe);
    }

    @Test
    public void testDismissRecipe_executeGetNextRecipeInteractor() throws Exception {
        presenter.dismissRecipe();
        assertNotNull(presenter.getMainView());
        verify(mainView).hideUIElements();
        verify(mainView).showProgress();
    }

    @Test
    public void testGetNextRecipe_executeGetNextRecipeInteractor() throws Exception {
        presenter.getNextRecipe();
        assertNotNull(presenter.getMainView());
        verify(mainView).hideUIElements();
        verify(mainView).showProgress();
        verify(nextMainInteractor).execute();
    }

    @Test
    public void testOnEvent_hasError() throws Exception {
        String errorMsg = "error";

        when(mainEvent.getError()).thenReturn(errorMsg);
        presenter.onEventMainThread(mainEvent);
        assertNotNull(presenter.getMainView());
        verify(mainView).hideProgress();
        verify(mainView).onGetRecipeError(mainEvent.getError());
    }

    @Test
    public void testOnNextEvent_setRecipeOnView() throws Exception{
        when(mainEvent.getType()).thenReturn(MainEvent.NEXT_EVENT);
        when(mainEvent.getRecipe()).thenReturn(recipe);

        presenter.onEventMainThread(mainEvent);

        assertNotNull(presenter.getMainView());
        verify(mainView).setRecipe(mainEvent.getRecipe());
    }

    @Test
    public void testOnSaveEvent_notifyViewAndCallGetNextRecipe() throws Exception{
        when(mainEvent.getType()).thenReturn(MainEvent.SAVE_EVENT);
        when(mainEvent.getRecipe()).thenReturn(recipe);

        presenter.onEventMainThread(mainEvent);

        assertNotNull(presenter.getMainView());
        verify(mainView).onRecipeSaved();
        verify(nextMainInteractor).execute();
    }

    @Test
    public void testImageReady_updateUi() throws Exception{
        presenter.imageReady();

        assertNotNull(presenter.getMainView());
        verify(mainView).hideProgress();
        verify(mainView).showUIElements();
    }

    @Test
    public void testImageError_updateUi() throws Exception{
        String error = "error";
        presenter.imageError(error);

        assertNotNull(presenter.getMainView());
        verify(mainView).onGetRecipeError(error);
    }

    @Test
    public void testGetView_returnsView() throws Exception {
       assertEquals(mainView, presenter.getView());
    }
}
