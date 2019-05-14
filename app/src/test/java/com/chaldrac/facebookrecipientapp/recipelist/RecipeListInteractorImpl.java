package com.chaldrac.facebookrecipientapp.recipelist;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.views.list.ListeInteractorImpl;
import com.chaldrac.facebookrecipientapp.views.list.ListeRepository;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class RecipeListInteractorImpl extends BaseTest {
    @Mock
    private ListeRepository listeRepository;
    private ListeInteractorImpl interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new ListeInteractorImpl(listeRepository);
    }

    @Test
    public void testExecute_shouldCallRepository() throws Exception{
        interactor.execute();
        verify(listeRepository).getSavedRecipes();
    }
}
