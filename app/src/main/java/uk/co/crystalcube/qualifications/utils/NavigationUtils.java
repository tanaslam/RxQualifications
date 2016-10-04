package uk.co.crystalcube.qualifications.utils;

import android.content.Context;
import android.content.Intent;

import uk.co.crystalcube.qualifications.model.Qualification;
import uk.co.crystalcube.qualifications.ui.SubjectsActivity;

/**
 * The helper class that groups app navigation functions such as starting new activities.
 *
 * Created by Tanveer Aslam on 02/10/2016.
 */
public class NavigationUtils {

    /**
     * Starts {@link SubjectsActivity} with {@link Qualification} id being passed as intent extra.
     * @param context Android context
     * @param qualification The {@link Qualification} object that contains corresponding subject list.
     */
    public static void startSubjectActivity(Context context, Qualification qualification) {
        Intent intent = new Intent(context.getApplicationContext(), SubjectsActivity.class);
        intent.putExtra(SubjectsActivity.KEY_EXTRA_QUALIFICATION_ID, qualification.getId());
        context.startActivity(intent);
    }
}
