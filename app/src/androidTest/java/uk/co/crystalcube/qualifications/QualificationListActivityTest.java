package uk.co.crystalcube.qualifications;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import uk.co.crystalcube.qualifications.ui.QualificationsActivity;
import uk.co.crystalcube.qualifications.ui.SubjectsActivity;

/**
 * Instrumentation test that test main activity layout composition
 * Created by Tanveer on 16/02/2015.
 */
public class QualificationListActivityTest extends ActivityInstrumentationTestCase2<QualificationsActivity> {

    private Activity activity;

    public QualificationListActivityTest() {
        super(QualificationsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        setActivityInitialTouchMode(false);
        activity = getActivity();

    }

    /**
     * An example test to test that main layout has a list view in it
     * @throws Exception
     */
    @SmallTest
    public void testUIComponent() throws Exception {

        assertNotNull(activity);

        //Get UI component
        View view  = activity.findViewById(R.id.container);

        //Validate that component exists
        assertNotNull(view);

        try {
           //TODO cast view to component and assert properties
        } catch (ClassCastException ce) {
            fail("Expecting a list view in activity view");
        }
    }

    /**
     * An example test that clicks a view to launch another activity and monitors the launch.
     * This may throw security exception if emulator is lock. This a known issue with ATF. See more
     * information here:
     *
     * https://code.google.com/p/robotium/issues/detail?id=1
     *
     * @throws Exception
     */
    @SmallTest
    public void testChildActivityLaunched() throws Exception {

        // add monitor to check for the second activity
        Instrumentation.ActivityMonitor monitor =
                getInstrumentation().
                        addMonitor(SubjectsActivity.class.getName(), null, false);

        TouchUtils.clickView(this, activity.findViewById(android.R.id.list));

        Activity startedActivity  = monitor.waitForActivityWithTimeout(10000);

        //Would be null if it was timed-out.
        assertNotNull("Activity didn't start in timely fashion", startedActivity);

    }
}
