package com.chaldrac.facebookrecipientapp;

import android.app.Application;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

public abstract class BaseTest {
    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

       /* if (InstrumentationRegistry.getInstrumentation().getContext() != null){
            ShadowApplication shadowApplication = Shadows.shadowOf((Application) ApplicationProvider.getApplicationContext());
            shadowApplication.grantPermissions("android.permission.INTERNET");
        }*/
    }
}
