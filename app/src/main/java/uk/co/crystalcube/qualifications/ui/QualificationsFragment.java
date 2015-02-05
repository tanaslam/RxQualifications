package uk.co.crystalcube.qualifications.ui;

/**
 * Created by tanny on 04/02/15.
 */

import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;

import java.util.List;

import uk.co.crystalcube.qualifications.R;
import uk.co.crystalcube.qualifications.model.Qualification;
import uk.co.crystalcube.qualifications.model.Qualifications;
import uk.co.crystalcube.qualifications.rest.QualificationsRestClient;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
public class QualificationsFragment extends AbstractListFragment {

    private static final String LOG_TAG = QualificationsFragment.class.getSimpleName();

    /**
     * List item click listener
     */
    public interface OnQualificationListItemListener {
        void onClick(Qualification qualification);
    }

    @Bean
    protected QualificationsRestClient dataClient;

    @Bean
    protected Qualifications qualifications;

    @AfterViews
    protected void init() {
        fetchQualifications();
    }

    @Background
    protected void fetchQualifications() {
        dataClient.getQualifications();
        showQualifications();
    }

    @UiThread
    protected void showQualifications() {
        if(qualifications != null) {
            adapter = new QualificationsListAdapter(qualifications.getQualificationList());
            setListAdapter(adapter);
        }
    }

    public class QualificationsListAdapter extends BaseAdapter {

        private List<Qualification> qualificationsList;

        public QualificationsListAdapter (List<Qualification> qualificationsList) {
            this.qualificationsList = qualificationsList;
        }
        @Override
        public int getCount() {
            return qualificationsList.size();
        }

        @Override
        public Object getItem(int position) {
            return qualificationsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return qualificationsList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if(convertView == null) {

                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_qualifications, null);

                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.list_item_name);
                holder.numberOfSubjects = (TextView) convertView.findViewById(R.id.list_item_subject_count);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

             holder.name.setText(qualificationsList.get(position).getName());
             holder.numberOfSubjects.setText(
                     String.valueOf(qualificationsList.get(position).getSubjects().size()));

             return convertView;
        }

        private class ViewHolder {
            ImageView icon;
            TextView name;
            TextView numberOfSubjects;
        }
    }
}
