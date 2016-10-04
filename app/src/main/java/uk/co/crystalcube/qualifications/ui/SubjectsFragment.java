package uk.co.crystalcube.qualifications.ui;

/**
 * Created by Tanveer Aslam on 02/10/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.crystalcube.qualifications.R;
import uk.co.crystalcube.qualifications.model.Qualification;
import uk.co.crystalcube.qualifications.model.Subject;

/**
 * A placeholder fragment containing a simple view.
 */
public class SubjectsFragment extends AbstractListFragment {

    public static final String FRAG_TAG = SubjectsFragment.class.getName();
    private static final String LOG_TAG = SubjectsFragment.class.getSimpleName();
    private static final String ARG_QUALIFICATION_ID = "arg_qualification_id";

    private String qualificationId;
    private Subscription subjectSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subjects, container, false);
    }

    /**
     * @see AbstractListFragment#init(View)
     */
    @Override
    protected void init(View view) {
        super.init(view);
        initFragmentArgs();
        subscribeObservable();
    }

    private void subscribeObservable() {

        //Create observable from callback that will called on subscribe()
        Observable<List<Subject>> subjectObservable = Observable.fromCallable(new Callable<List<Subject>>() {
            @Override
            public List<Subject> call() throws Exception {
                Qualification qualification = restClient.getQualification(qualificationId);
                if (qualification != null) {
                    return qualification.getSubjects();
                }
                return Collections.emptyList();
            }
        });

        //Subscribe to observable with inline subscriber
        subjectSubscription = subjectObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new Subscriber<List<Subject>>() {
                    @Override
                    public void onCompleted() {
                        Log.v(LOG_TAG, "subjectObservable->onCompleted()");
                        //NO-OP ignore silently
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(LOG_TAG, "subjectObservable->onError(Throwable e)", e);
                        //Handle rest exception here
                    }

                    @Override
                    public void onNext(List<Subject> subjects) {
                        showSubjects(subjects);
                    }
                });
    }

    private void initFragmentArgs() {
        qualificationId = getArguments().getString(ARG_QUALIFICATION_ID);
    }

    private void showSubjects(List<Subject> subjects) {
        if (qualificationId != null) {
            adapter = new SubjectsListAdapter(getContext(), subjects);
            recyclerView.setAdapter(adapter);
        }
    }

    public static Fragment newInstance(@NonNull String qualificationId) {

        SubjectsFragment fragment = new SubjectsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_QUALIFICATION_ID, qualificationId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subjectSubscription != null && !subjectSubscription.isUnsubscribed()) {
            subjectSubscription.unsubscribe();
        }
    }

    public static class SubjectsListAdapter extends ReactiveAdapter<Subject> {


        SubjectsListAdapter(Context context, List<Subject> subjectList) {
            super(context, subjectList);
        }

        @Override
        public AbstractViewHolder<Subject> onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_subjects, parent, false);
            return new SubjectItemViewHolder(itemView);
        }

        /**
         * Adapter's view-holder to
         */
        private static class SubjectItemViewHolder extends AbstractViewHolder<Subject> {

            private TextView name;

            private SubjectItemViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.list_item_name);
            }

            @Override
            void bind(Subject item) {
                setItemColour(item);
                name.setText(item.getTitle() == null ? item.getId() : item.getTitle());
            }

            private void setItemColour(Subject item) {

                String colourValue = item.getColour();

                if (colourValue != null && !colourValue.isEmpty()) {
                    //Set a default colour if parsing was failed
                    int colour = Color.parseColor("#FFA6B6");
                    try {
                        colour = Color.parseColor(colourValue);
                    } catch (IllegalArgumentException e) {
                        Log.w(LOG_TAG, "Setting default row colour");
                    }

                    itemView.setBackgroundColor(colour);
                }
            }
        }
    }
}
