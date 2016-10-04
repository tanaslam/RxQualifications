package uk.co.crystalcube.qualifications;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.TestSuite;

/**
 * Created by Tanveer Aslam on 16/02/2015.
 */
public class ApplicationTestSuite {

    public static TestSuite suite() {
        return new TestSuiteBuilder(ApplicationTestSuite.class)
                .includeAllPackagesUnderHere()
                .build();
    }
}
