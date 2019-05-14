package com.chaldrac.facebookrecipientapp.api;

import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.BuildConfig;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.views.main.MainRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Random;

import static junit.framework.TestCase.*;

@RunWith(RobolectricTestRunner.class)
@Config
public class RecipeServiceTest extends BaseTest {
    private RecipeService service;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        RecipeClient client = new RecipeClient();
        service = client.getRecipeService();
    }

    @Test
    public void doSearch_getRecipeFromBackend() throws Exception {
        String sort = MainRepository.Companion.getRECENT_SORT();
        int count = MainRepository.Companion.getCOUNT();
        int page = 1;
        Call<RecipeSearchResponse> call =  service.search(BuildConfig.FOOD_API_KEY, sort, count, page);

        Response<RecipeSearchResponse> response = (call).execute();
        assertTrue(response.isSuccessful());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertNotNull(recipeSearchResponse);
        assertEquals(1, recipeSearchResponse.getCount());

        Recipe recipe = recipeSearchResponse.getFirstRecipe();
        assertNotNull(recipe);
    }

    @Test
    public void doSearch_getNoRecipeFromBackend() throws Exception {
        String sort = MainRepository.Companion.getRECENT_SORT();
        int count = MainRepository.Companion.getCOUNT();
        int page = 1000000;
        Call<RecipeSearchResponse> call =  service.search(BuildConfig.FOOD_API_KEY, sort, count, page);

        Response<RecipeSearchResponse> response = (call).execute();
        assertTrue(response.isSuccessful());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertNotNull(recipeSearchResponse);
        assertEquals(0, recipeSearchResponse.getCount());

        Recipe recipe = recipeSearchResponse.getFirstRecipe();
        assertNull(recipe);
    }

    @Test
    public void doSearch_getRandomRecipeFromBackend() throws Exception {
        String sort = MainRepository.Companion.getRECENT_SORT();
        int count = MainRepository.Companion.getCOUNT();
        int page = new Random().nextInt(MainRepository.Companion.getRECIPE_RANGE());
        Call<RecipeSearchResponse> call =  service.search(BuildConfig.FOOD_API_KEY, sort, count, page);

        Response<RecipeSearchResponse> response = (call).execute();
        assertTrue(response.isSuccessful());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertNotNull(recipeSearchResponse);
        if (recipeSearchResponse.getCount() == 1){
            Recipe recipe = recipeSearchResponse.getFirstRecipe();
            assertNotNull(recipe);
        }else{
            System.out.println("Invalide recipe, try again");
        }

    }
}
