package com.chaldrac.facebookrecipientapp.recipemain;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.R;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.lib.base.ImageLoader;
import com.chaldrac.facebookrecipientapp.support.ShadowImageView;
import com.chaldrac.facebookrecipientapp.views.login.ui.LoginActivity;
import com.chaldrac.facebookrecipientapp.views.main.MainPresenter;
import com.chaldrac.facebookrecipientapp.views.main.ui.MainActivity;
import com.chaldrac.facebookrecipientapp.views.main.ui.MainView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowImageView.class})
public class RecipeMainActivityTest extends BaseTest {
    @Mock
    private Recipe currentRecipe;
    @Mock
    private ImageLoader imageLoader;
    @Mock
    private MainPresenter mainPresenter;

    private MainActivity mockActivity;
    private MainView view;
    private ShadowActivity shadowAcivity;


    @Override
    public void setUp() throws Exception {
        super.setUp();

        mockActivity = Mockito.spy(Robolectric.buildActivity(MainActivity.class).create().visible().get());
        view = mockActivity;
        shadowAcivity = shadowOf(mockActivity);
    }

    @Test
    public void testOnActivityCreated_getNextRecipe() {
       /* mockActivity.onDestroy();
        mockActivity.onCreate(null);*/
        verify(mockActivity).setupPresenter();
    }

    @Test
    public void testOnActivityDestroyed_destroyPresenter() {
        // mockActivity.onDestroy();
        verify(mockActivity).destroyPresenter();
    }

    @Test
    public void testLogoutMenuClicked_ShouldLaunchLoginActivity() {
        shadowAcivity.clickMenuItem(R.id.action_logout);
        Intent intent = shadowAcivity.peekNextStartedActivity();
        assertEquals(new ComponentName(mockActivity, LoginActivity.class), intent.getComponent());
    }

    @Test
    public void testListMenuClicked_ShouldLaunchRecipeListActivity() {
        shadowAcivity.clickMenuItem(R.id.action_list);
        Intent intent = shadowAcivity.peekNextStartedActivity();
        assertEquals(new ComponentName(mockActivity, ListActivity.class), intent.getComponent());
    }

    @Test
    public void testKeepButtonClicked_ShouldSaveRecipe() {
         //mockActivity.onDestroy();
       //  mockActivity.onCreate(null);
        mockActivity.setRecipe(currentRecipe);

        ImageButton imgKeep = mockActivity.findViewById(R.id.imgKeep);
        imgKeep.performClick();
        verify(mockActivity).onKeep();
        verify(mainPresenter).saveRecipe(currentRecipe);
    }

    @Test
    public void testDismissButtonClicked_ShouldDismissRecipe() {
        // mockActivity.onDestroy();
        // mockActivity.onCreate(null);
        ImageButton imgDismiss = mockActivity.findViewById(R.id.imgDismiss);
        imgDismiss.performClick();
        verify(mockActivity).onDismiss();
        //verify(mainPresenter).dismissRecipe();
    }

    @Test
    public void testOnSwipeToKeep_ShouldSaveRecipe() {
        //  mockActivity.onDestroy();
        // mockActivity.onCreate(null);
        mockActivity.setRecipe(currentRecipe);

        ImageView imgRecipe = mockActivity.findViewById(R.id.imgRecipe);
        ShadowImageView shadowImage = Shadow.extract(imgRecipe);
        shadowImage.performSwipe(200, 200, 500, 250, 50);
        verify(mockActivity).onKeep();
        //verify(mainPresenter).saveRecipe(currentRecipe);
    }


    @Test
    public void testOnSwipeToDismiss_ShouldDiscardRecipe() {
        //mockActivity.onDestroy();
        //mockActivity.onCreate(null);
        ImageView imgRecipe = mockActivity.findViewById(R.id.imgRecipe);
        ShadowImageView shadowImage = Shadow.extract(imgRecipe);
        shadowImage.performSwipe(200, 200, -500, 250, 50);
        verify(mockActivity).onDismiss();
    }

    @Test
    public void testShowProgress_progressBarShouldBeVisible() {
        view.showProgress();

        ProgressBar progressBar = mockActivity.findViewById(R.id.progressBar);
        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void testHideProgress_progressBarShouldBeGone() {
        view.hideProgress();

        ProgressBar progressBar = mockActivity.findViewById(R.id.progressBar);
        assertEquals(View.GONE, progressBar.getVisibility());
    }

    @Test
    public void testShowUIElements_buttonsShouldBeVisible() {
        view.showUIElements();

        ImageButton imgKeep = mockActivity.findViewById(R.id.imgKeep);
        ImageButton imgDismiss = mockActivity.findViewById(R.id.imgDismiss);

        assertEquals(View.VISIBLE, imgKeep.getVisibility());
        assertEquals(View.VISIBLE, imgDismiss.getVisibility());
    }

    @Test
    public void testHideUIElements_buttonsShouldBeGone() {
        view.hideUIElements();

        ImageButton imgKeep = mockActivity.findViewById(R.id.imgKeep);
        ImageButton imgDismiss = mockActivity.findViewById(R.id.imgDismiss);

        assertEquals(View.GONE, imgKeep.getVisibility());
        assertEquals(View.GONE, imgDismiss.getVisibility());
    }

    @Test
    public void testSetRecipe_ImageLoaderShouldBeCalled() {
        String url = "http://galileo.edu";
        when(currentRecipe.getImageUrl()).thenReturn(url);

        view.setRecipe(currentRecipe);
        ImageView imgRecipe = mockActivity.findViewById(R.id.imgRecipe);
        verify(mockActivity).setRecipe(currentRecipe);
        assertNotNull(currentRecipe.getImageUrl());
        verify(imageLoader).load(imgRecipe, currentRecipe.getImageUrl());

    }

    @Test
    public void testSaveAnimation_AnimationShouldBeStarted() {
        view.saveAnimation();

        ImageView imgRecipe = mockActivity.findViewById(R.id.imgRecipe);
        assertNotNull(imgRecipe.getAnimation());
        assertTrue(imgRecipe.getAnimation().hasStarted());
    }

    @Test
    public void testDismissAnimation_AnimationShouldBeStarted() {
        view.dismissAnimation();

        ImageView imgRecipe = mockActivity.findViewById(R.id.imgRecipe);
        assertNotNull(imgRecipe.getAnimation());
        assertTrue(imgRecipe.getAnimation().hasStarted());
    }


}
