package uk.co.crystalcube.qualifications;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;

import com.robotium.solo.Solo;

import uk.co.crystalcube.qualifications.ui.SubjectsActivity_;
import uk.co.crystalcube.qualifications.ui.SubjectsFragment_;

public class SubjectsActivityTest extends
        ActivityInstrumentationTestCase2<SubjectsActivity_> {

    private FragmentActivity activity;
    private Solo solo;

    public SubjectsActivityTest() {
        super(SubjectsActivity_.class);
    }

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        setActivityInitialTouchMode(false);
        activity = getActivity();

        solo = new Solo(getInstrumentation(), activity);
    }

    @SmallTest
    public void testUiComponent() throws Exception {

        solo.waitForActivity(activity.getClass());
        solo.assertCurrentActivity("Current activity is not expected activity", activity.getClass());

        Fragment frag = activity.getSupportFragmentManager()
                .findFragmentByTag(SubjectsFragment_.class.getSimpleName());

        assertNotNull("Expected fragment is null", frag);
        assertNotNull(frag.getView());

        ListView list = (ListView) frag.getView().findViewById(android.R.id.list);

        assertNotNull(list);
        assertEquals(list.getAdapter().getCount(), 0);
    }
}