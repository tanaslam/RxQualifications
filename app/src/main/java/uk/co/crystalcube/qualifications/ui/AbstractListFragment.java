package uk.co.crystalcube.qualifications.ui;

import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;

import uk.co.crystalcube.qualifications.model.Qualification;

/**
 * Created by tanny on 04/02/15.
 */
@EFragment
public class AbstractListFragment extends ListFragment {

    private static final String LOG_TAG = AbstractListFragment.class.getSimpleName();

    protected BaseAdapter adapter;
    /**
     * List item click listener
     */
    public interface OnListItemListener {
        void onClick(Object item);
    }

    @ItemClick(android.R.id.list)
    protected void showSubjects(int position) {
        try {
            ((OnListItemListener) getActivity())
                    .onClick(adapter.getItem(position));
        } catch(ClassCastException ce) {
            Log.e(LOG_TAG, "Parent activity must implement OnQualificationListItemListener");
        }
    }
}
