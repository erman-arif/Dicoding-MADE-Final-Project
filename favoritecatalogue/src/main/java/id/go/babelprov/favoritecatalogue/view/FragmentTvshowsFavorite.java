package id.go.babelprov.favoritecatalogue.view;


import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.go.babelprov.favoritecatalogue.R;
import id.go.babelprov.favoritecatalogue.adapter.FavoriteTvshowAdapter;
import id.go.babelprov.favoritecatalogue.helper.MappingHelper;
import id.go.babelprov.favoritecatalogue.helper.UtilHelper;
import id.go.babelprov.favoritecatalogue.model.DbContract;
import id.go.babelprov.favoritecatalogue.model.Favorite;


public class FragmentTvshowsFavorite extends Fragment implements LoadFavTvshowsCallback{

    private static View view;
    private ProgressBar progressBar;
    private RecyclerView rvFavTvshows;
    private FavoriteTvshowAdapter favoriteTvshowAdapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FragmentTvshowsFavorite() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tvshows_favorite, container, false);

        // Setup RecyclerView
        progressBar = view.findViewById(R.id.progressBar);
        rvFavTvshows = view.findViewById(R.id.rv_tvshow_favorite_list);
        rvFavTvshows.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavTvshows.setHasFixedSize(true);
        favoriteTvshowAdapter = new FavoriteTvshowAdapter(this.getActivity());
        rvFavTvshows.setAdapter(favoriteTvshowAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());

        PackageManager pm = getContext().getPackageManager();
        boolean isInstalled = UtilHelper.isPackageInstalled("id.go.babelprov.moviecatalogues5", pm);

        if(isInstalled) {
            this.getActivity().getContentResolver().registerContentObserver(DbContract.FavoriteColumns.CONTENT_URI_TVSHOW, true, myObserver);

            if (savedInstanceState == null) {
                new LoadFavTvshowsAsync(getContext(), this).execute();
            } else {
                ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
                if (list != null) {
                    favoriteTvshowAdapter.setListTvshows(list);
                }
            }
        }
        else {
            Toast.makeText(getContext(),getString(R.string.package_moviecatalogue_not_exist),Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    //-----------------------------------------------------------
    // onSaveInstanceState
    //-----------------------------------------------------------
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favoriteTvshowAdapter.getListFavTvshows());
    }

    //-----------------------------------------------------------
    // preExecute
    //-----------------------------------------------------------
    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    //-----------------------------------------------------------
    // postExecute
    //-----------------------------------------------------------
    @Override
    public void postExecute(ArrayList<Favorite> favorites) {
        if (favorites.size() > 0) {
            favoriteTvshowAdapter.setListTvshows(favorites);
        } else {
            favoriteTvshowAdapter.setListTvshows(new ArrayList<Favorite>());
        }
    }

    //-----------------------------------------------------------
    // LoadFavMoviesAsync
    //-----------------------------------------------------------
    private static class LoadFavTvshowsAsync extends AsyncTask<Void, Void, ArrayList<Favorite>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavTvshowsCallback> weakCallback;

        private LoadFavTvshowsAsync(Context context, LoadFavTvshowsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
            UtilHelper.showLoading(view,true);
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DbContract.FavoriteColumns.CONTENT_URI_TVSHOW, null, null, null, null);

            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorites) {
            super.onPostExecute(favorites);
            weakCallback.get().postExecute(favorites);
            UtilHelper.showLoading(view,false);
        }
    }


    //-----------------------------------------------------------
    // DataObserver
    //-----------------------------------------------------------
    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadFavTvshowsAsync(context, (LoadFavTvshowsCallback) context).execute();
        }
    }
}

interface LoadFavTvshowsCallback {
    void preExecute();
    void postExecute(ArrayList<Favorite> favorites);
}
