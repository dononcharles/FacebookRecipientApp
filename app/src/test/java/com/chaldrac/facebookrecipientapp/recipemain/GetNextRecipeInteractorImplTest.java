package com.chaldrac.facebookrecipientapp.recipemain;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.views.main.GetNextMainInteractor;
import com.chaldrac.facebookrecipientapp.views.main.GetNextMainInteractorImpl;
import com.chaldrac.facebookrecipientapp.views.main.MainRepository;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class GetNextRecipeInteractorImplTest extends BaseTest {
    @Mock
    private MainRepository mainRepository;
    private GetNextMainInteractorImpl interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new GetNextMainInteractorImpl(mainRepository);
    }

    @Test
    public void testExecute_callRepository() throws Exception {
        interactor.execute();
        verify(mainRepository).getNextRecipe();
    }


}
