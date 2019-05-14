package com.chaldrac.facebookrecipientapp.recipemain;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.BuildConfig;
import com.chaldrac.facebookrecipientapp.api.RecipeSearchResponse;
import com.chaldrac.facebookrecipientapp.api.RecipeService;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.lib.base.EventBus;
import com.chaldrac.facebookrecipientapp.views.main.GetNextMainInteractorImpl;
import com.chaldrac.facebookrecipientapp.views.main.MainRepository;
import com.chaldrac.facebookrecipientapp.views.main.MainRepositoryImpl;
import com.chaldrac.facebookrecipientapp.views.main.event.MainEvent;
import com.dbflow5.database.DatabaseWrapper;
import okhttp3.Request;
import org.apache.tools.ant.Main;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeMainRepositoryImplTest extends BaseTest {
    @Mock
    private RecipeService service;
    @Mock
    private EventBus eventBus;

    private MainRepository repository;

    @Mock
    Recipe recipe;

    private ArgumentCaptor<MainEvent> mainEventArgumentCaptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        repository = new MainRepositoryImpl(eventBus, service);
        mainEventArgumentCaptor = ArgumentCaptor.forClass(MainEvent.class);
    }

    /*  @Test
      public void testSaveRecipeCall_eventPosted() throws Exception {
          when(recipe.exists()).thenReturn(true);
          repository.saveRecipe(recipe);

          verify(eventBus).post(mainEventArgumentCaptor.capture());
          MainEvent event = mainEventArgumentCaptor.getValue();

          assertEquals(MainEvent.SAVE_EVENT, event.getType());
          assertNull(event.getError());
          assertNull(event.getRecipe());
      }
  */
    private Call<RecipeSearchResponse> buildCall(final boolean success, String errorMsg) {
        Call<RecipeSearchResponse> call = new Call<RecipeSearchResponse>() {
            @NotNull
            @Override
            public Response<RecipeSearchResponse> execute() throws IOException {
                Response<RecipeSearchResponse> result = null;
                if (success) {
                    RecipeSearchResponse response = new RecipeSearchResponse();
                    response.setCount(1);
                    response.setRecipes(Arrays.asList(recipe));
                    result = Response.success(response);
                } else {
                    result = Response.error(null, null);
                }
                return result;
            }

            @Override
            public void enqueue(@NotNull Callback<RecipeSearchResponse> callback) {
                if (success) {
                    try {
                        callback.onResponse(this, execute());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    callback.onFailure(this, new Throwable(errorMsg));
                }
            }

            @Override
            public boolean isExecuted() {
                return true;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<RecipeSearchResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        return call;
    }

    @Test
    public void testGetNextRecipeCalled_apiServiceSuccessCall_EventPosted() throws Exception {
        int recipePage = new Random().nextInt(MainRepository.Companion.getRECIPE_RANGE());
        when(service.search(BuildConfig.FOOD_API_KEY,
                MainRepository.Companion.getRECENT_SORT(),
                MainRepository.Companion.getCOUNT(),
                recipePage)).thenReturn(buildCall(true, null));

        repository.setRecipePage(recipePage);
        repository.getNextRecipe();

        verify(service).search(BuildConfig.FOOD_API_KEY,
                MainRepository.Companion.getRECENT_SORT(),
                MainRepository.Companion.getCOUNT(),
                recipePage);

        verify(eventBus).post(mainEventArgumentCaptor.capture());
        MainEvent event = mainEventArgumentCaptor.getValue();
        assertEquals(MainEvent.NEXT_EVENT, event.getType());

        assertNull(event.getError());
        assertNotNull(event.getRecipe());
        assertEquals(recipe, event.getRecipe());
    }

    @Test
    public void testGetNextRecipeCalled_apiServiceFailedCall_EventPosted() throws Exception {
        String err = "error";
        int recipePage = new Random().nextInt(MainRepository.Companion.getRECIPE_RANGE());
        when(service.search(BuildConfig.FOOD_API_KEY,
                MainRepository.Companion.getRECENT_SORT(),
                MainRepository.Companion.getCOUNT(),
                recipePage)).thenReturn(buildCall(false, err));
        repository.setRecipePage(recipePage);
        repository.getNextRecipe();


        verify(service).search(BuildConfig.FOOD_API_KEY,
                MainRepository.Companion.getRECENT_SORT(),
                MainRepository.Companion.getCOUNT(),
                recipePage);

        verify(eventBus).post(mainEventArgumentCaptor.capture());
        MainEvent event = mainEventArgumentCaptor.getValue();

        assertEquals(MainEvent.NEXT_EVENT, event.getType());
        assertNotNull(event.getError());
        assertNull(event.getRecipe());
        assertEquals(err, event.getError());
    }

/*    @Test
    public void test() throws Exception {

    }*/


}
