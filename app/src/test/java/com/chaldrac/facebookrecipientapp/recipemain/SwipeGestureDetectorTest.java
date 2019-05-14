package com.chaldrac.facebookrecipientapp.recipemain;

import android.view.MotionEvent;
import com.chaldrac.facebookrecipientapp.BaseTest;
import com.chaldrac.facebookrecipientapp.views.main.ui.SwipeGestureDetector;
import com.chaldrac.facebookrecipientapp.views.main.ui.SwipeGestureListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config
public class SwipeGestureDetectorTest extends BaseTest {
    @Mock
    private SwipeGestureListener swipeGestureListener;

    private SwipeGestureDetector detector;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        detector = new SwipeGestureDetector(swipeGestureListener);
    }

    @Test
    public void testSwipeRight_ShouldCallKeepOnListener() throws Exception{
        long downTime = 0;
        long moveTime = downTime + 500;
        long upTime = downTime + 1000;
        float xStart = 200;
        float yStart = 200;
        float xEnd = 500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);

        float velocity = 120;

        detector.onFling(e1, e2, velocity, 0);
        verify(swipeGestureListener).onKeep();
    }

    @Test
    public void testSwipeLeft_ShouldCallDismissOnListener() throws Exception{
        long downTime = 0;
        long moveTime = downTime + 500;
        long upTime = downTime + 1000;
        float xStart = 200;
        float yStart = 200;
        float xEnd = -500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);

        float velocity = 120;

        detector.onFling(e1, e2, velocity, 0);
        verify(swipeGestureListener).onDismiss();
    }
}
