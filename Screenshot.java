package com.example.tiana.visualisierungaccelerometer;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Tiana on 04.11.17.
 */

public class Screenshot {

    public static Bitmap takeScreenshot (View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        return b;
    }

    public static Bitmap takeScreenshotOfRootView (View v) {
        return takeScreenshot(v.getRootView());
    }


}
