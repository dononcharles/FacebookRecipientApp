package com.chaldrac.facebookrecipientapp.recipemain;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.views.main.GetNextMainInteractorImpl;
import com.chaldrac.facebookrecipientapp.views.main.MainRepository;
import com.chaldrac.facebookrecipientapp.views.main.SaveRecipeInteractor;
import com.chaldrac.facebookrecipientapp.views.main.SaveRecipeInteractorImpl;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class SaveRecipeInteractorImplTest extends BaseTest {
    @Mock
    private MainRepository mainRepository;
    private SaveRecipeInteractor interactor;
    @Mock
    Recipe recipe;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new SaveRecipeInteractorImpl(mainRepository);
    }

    @Test
    public void testExecute_callRepository() throws Exception {
        interactor.execute(recipe);
        verify(mainRepository).saveRecipe(recipe);
    }


}
