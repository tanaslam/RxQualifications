package uk.co.crystalcube.qualifications;

import android.app.Application;
import android.util.Log;

/**
 * Created by Tanveer Aslam on 23/02/2015.
 */
public class QualificationsApp extends Application {

    private static final String LOG_TAG = QualificationsApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "Application launched!");

        // Init application wide objects here
        // i.e. crash reporting framework, analytics or
        // distribution variants' configuration factory.
    }
}
