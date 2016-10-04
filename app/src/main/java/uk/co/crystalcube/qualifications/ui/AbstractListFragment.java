package uk.co.crystalcube.qualifications.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;
import uk.co.crystalcube.qualifications.R;
import uk.co.crystalcube.qualifications.rest.QualificationsRestClient;


/**
 * The abstract class that initialises common views e.g {@link RecyclerView} and it's adapter among children, set up
 * rest client framework and provides abstarction for view-holder.
 * <p>
 * Created by Tanveer Aslam on 25/09/16.
 */

public class AbstractListFragment<T> extends Fragment {

    private static final String LOG_TAG = AbstractListFragment.class.getSimpleName();

    protected OnItemClickListener<T> listener;
    protected ReactiveAdapter<T> adapter;
    protected RecyclerView recyclerView;
    protected QualificationsRestClient restClient;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    /**
     * Initialises fragment views and rest client. A derived class wishes to extend setup
     * must override and call super method.
     *
     * @param view The fragment view.
     */
    protected void init(View view) {
        initViews(view);
        initRestClient();
    }

    private void initViews(View fragView) {
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initRestClient() {
        restClient = QualificationsRestClient.build();
    }

    /**
     * The {@link RecyclerView.Adapter} abstract class that handles view binding and delegates
     * view click to Observable.
     */
    public static abstract class ReactiveAdapter<T> extends RecyclerView.Adapter<AbstractViewHolder<T>> {

        protected Context context;

        private final PublishSubject<T> onClickSubject = PublishSubject.create();
        private List<T> dataSet;

        ReactiveAdapter(Context context, List<T> dataSet) {
            this.context = context;
            this.dataSet = dataSet;
        }

        @Override
        public int getItemCount() {
            return dataSet == null ? 0 : dataSet.size();
        }

        @Override
        public void onBindViewHolder(final AbstractViewHolder<T> holder, int position) {

            final T element = dataSet.get(position);

            holder.bind(element);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(LOG_TAG, "View with element: " + element + " clicked");
                    onClickSubject.onNext(element);
                }
            });
        }

        /**
         * Sets subscriber to 'onClickSubject' and returns corresponding subscription.
         *
         * @param subscriber The view onClick subscriber.
         * @return The corresponding {@link Subscription}
         */
        public Subscription subscribeItemClick(Subscriber<T> subscriber) {
            return onClickSubject.subscribe(subscriber);
        }

        /**
         * Gets all clicks observed on adopter views.
         *
         * @return {@link Observable}
         */
        public Observable<T> getPositionClicks() {
            return onClickSubject.asObservable();
        }
    }

    protected abstract static class AbstractViewHolder<T> extends RecyclerView.ViewHolder {

        AbstractViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * The abstract method that binds data with item view.
         *
         * @param item The associated data item that will bind with view.
         */
        abstract void bind(T item);
    }

    /**
     * The callback interface that relays adapter item clicks
     */
    protected interface OnItemClickListener<T> {
        void onItemClick(T item);
    }
}
