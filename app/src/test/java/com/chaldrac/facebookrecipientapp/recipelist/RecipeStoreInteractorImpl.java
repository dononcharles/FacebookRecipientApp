package com.chaldrac.facebookrecipientapp.recipelist;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.views.list.ListeInteractorImpl;
import com.chaldrac.facebookrecipientapp.views.list.ListeRepository;
import com.chaldrac.facebookrecipientapp.views.list.StoreListeInteractorImpl;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class RecipeStoreInteractorImpl extends BaseTest {
    @Mock
    private Recipe recipe;
    @Mock
    private ListeRepository listeRepository;
    private StoreListeInteractorImpl interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new StoreListeInteractorImpl(listeRepository);
    }

    @Test
    public void testExecuteUpdate_CallRepo() throws Exception {
        interactor.executeUpdate(recipe);
        verify(listeRepository).updateRecipes(recipe);
    }

    @Test
    public void testExecuteDelete_CallRepo() throws Exception {
        interactor.executeDelete(recipe);
        verify(listeRepository).removeRecipes(recipe);
    }
}
