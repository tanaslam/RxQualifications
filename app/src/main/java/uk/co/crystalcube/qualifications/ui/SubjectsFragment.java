package uk.co.crystalcube.qualifications.ui;

/**
 * Created by tanny on 04/02/15.
 */

import android.graphics.Color;
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
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;

import java.util.List;

import uk.co.crystalcube.qualifications.R;
import uk.co.crystalcube.qualifications.model.Qualification;
import uk.co.crystalcube.qualifications.model.Qualifications;
import uk.co.crystalcube.qualifications.model.Subject;
import uk.co.crystalcube.qualifications.rest.QualificationsRestClient;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_subjects)
public class SubjectsFragment extends AbstractListFragment {

    private static final String LOG_TAG = SubjectsFragment.class.getSimpleName();

    @FragmentArg
    protected String qualificationId;

    @Bean
    protected QualificationsRestClient dataClient;

    @Bean
    protected Qualifications qualifications;

    @AfterViews
    protected void showSubjects() {
        if(qualifications != null) {
            adapter = new SubjectsListAdapter(
                    qualifications.getSubjectForQualification(qualificationId));
            setListAdapter(adapter);
        }
    }

    public class SubjectsListAdapter extends BaseAdapter {

        private List<Subject> subjectList;

        public SubjectsListAdapter (List<Subject> subjectList) {
            this.subjectList = subjectList;
        }

        @Override
        public int getCount() {
            return subjectList == null ? 0 : subjectList.size();
        }

        @Override
        public Object getItem(int position) {
            return subjectList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return subjectList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if(convertView == null) {

                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_qualifications, null);

                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.list_item_name);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

             holder.name.setText(subjectList.get(position).getTitle());


            String colourValue = subjectList.get(position).getColour();

            if(colourValue != null && !colourValue.isEmpty()) {

                //Set a default colour if parsing fails
                int colour = Color.parseColor("#FFA6B6");

                try {
                    colour = Color.parseColor(
                            colourValue);
                } catch (IllegalArgumentException e) {
                    Log.w(LOG_TAG, "Setting default row colour");
                }

                convertView.setBackgroundColor(colour);
            }

            return convertView;
        }

        private class ViewHolder {
            TextView name;
        }
    }
}
