package uk.co.crystalcube.qualifications.ui;

/**
 * Created by Tanveer Aslam on 02/10/16.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.crystalcube.qualifications.R;
import uk.co.crystalcube.qualifications.model.Qualification;

/**
 * A placeholder fragment containing a simple view.
 */
public class QualificationsFragment extends AbstractListFragment<Qualification> {

    public static final String FRAG_TAG = QualificationsFragment.class.getName();
    private static final String LOG_TAG = QualificationsFragment.class.getSimpleName();

    private Subscription subscription;


    /**
     * Empty default constructor
     */
    public QualificationsFragment() {
    }

    /**
     * Fragment factory method.
     *
     * @return The instance of this class
     */
    public static Fragment newInstance() {
        return new QualificationsFragment();
    }

    /**
     * @see Fragment#onAttach(Context)
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context == null) {
            Log.e(LOG_TAG, "The context is null! Fragment transaction was performed out of context");
            return;
        }

        try {
            listener = (OnQualificationOnItemClickListener) getContext();
        } catch (ClassCastException ce) {
            Log.e(LOG_TAG, context.getClass().getName() + " must implement OnQualificationOnItemClickListener");
        }
    }

    /**
     * @see Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qualifications, container, false);
    }

    /**
     * @see Fragment#onViewCreated(View, Bundle)
     */
    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected void init(View view) {
        super.init(view);
        subscribeObservable();
    }

    private void subscribeObservable() {
        final Observable<List<Qualification>> qualificationObserver = Observable.create(new Observable.OnSubscribe<List<Qualification>>() {
            @Override
            public void call(Subscriber<? super List<Qualification>> subscriber) {
                List<Qualification> qualifications = restClient.getQualifications();
                subscriber.onNext(qualifications);
                subscriber.onCompleted();
            }
        });

        subscription = qualificationObserver
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Qualification>>() {
                    @Override
                    public void onCompleted() {
                        Log.v(LOG_TAG, "qualificationObserver->onCompleted()");
                        //NO-OP ignore silently
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(LOG_TAG, "qualificationObserver->onError(Throwable e)",  e);
                        //Handle rest exception here
                    }

                    @Override
                    public void onNext(List<Qualification> qualifications) {
                        showQualifications(qualifications);
                    }
                });
    }

    private void showQualifications(List<Qualification> qualifications) {
        if (qualifications != null) {
            adapter = new QualificationsListAdapter(getContext(), qualifications);
            adapter.subscribeItemClick(new Subscriber<Qualification>() {
                @Override
                public void onCompleted() {
                    //NO-OP
                }

                @Override
                public void onError(Throwable e) {
                    //NO-OP
                }

                @Override
                public void onNext(Qualification item) {
                    listener.onItemClick(item);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * QualificationsListAdapter class that inflates {@link RecyclerView} item layouts.
     */
    public class QualificationsListAdapter extends ReactiveAdapter<Qualification> {

        private static final int QUALIFICATION_SIMPLE_ITEM_VIEW = 1;
        private static final int QUALIFICATION_DECORATED_ITEM_VIEW = 2;

        QualificationsListAdapter(Context context, List<Qualification> qualificationsList) {
            super(context, qualificationsList);
        }

        @Override
        public int getItemViewType(int position) {
            return QUALIFICATION_SIMPLE_ITEM_VIEW;
        }

        @Override
        public AbstractViewHolder<Qualification> onCreateViewHolder(ViewGroup parent, int viewType) {

            switch (viewType) {
                case QUALIFICATION_SIMPLE_ITEM_VIEW:
                    View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_qualifications, parent, false);
                    return new QualificationsItemViewHolder(itemView);

                case QUALIFICATION_DECORATED_ITEM_VIEW:
                    //TODO inflate more fancy view here and init itemView
                    break;

                default:
                    Log.e(LOG_TAG, "Unsupported view type: " +
                            viewType + " Do you intended to implemented this case?");
            }

            return null;

        }


        /**
         * Concrete {@link RecyclerView.ViewHolder} class for {@link Qualification} list item.
         */
        private class QualificationsItemViewHolder extends AbstractViewHolder<Qualification> {

            private ImageView icon;
            private TextView name;
            private TextView numberOfSubjects;

            /**
             * View holder constructor.
             *
             * @see android.support.v7.widget.RecyclerView.ViewHolder#ViewHolder(View)
             */
            QualificationsItemViewHolder(View itemView) {

                super(itemView);

                icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
                name = (TextView) itemView.findViewById(R.id.list_item_name);
                numberOfSubjects = (TextView) itemView.findViewById(R.id.list_item_subject_count);
            }

            /**
             * @see uk.co.crystalcube.qualifications.ui.AbstractListFragment.AbstractViewHolder#bind(Object)
             */
            @Override
            void bind(Qualification item) {
                icon.setImageResource(android.R.drawable.btn_star_big_on);
                name.setText(item.getName());
                numberOfSubjects.setText(
                        String.valueOf(item.getSubjects() == null ? 0 : item.getSubjects().size()));
            }
        }

    }

    public interface OnQualificationOnItemClickListener extends OnItemClickListener<Qualification> {
    }

}
