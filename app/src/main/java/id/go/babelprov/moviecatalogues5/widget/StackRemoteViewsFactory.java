package id.go.babelprov.moviecatalogues5.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.model.FavoriteDao;
import id.go.babelprov.moviecatalogues5.model.FavoriteRoomDatabase;


public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final ArrayList<Favorite> mWidgetItems = new ArrayList<>();
    private FavoriteRoomDatabase database;


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        final long identityToken = Binder.clearCallingIdentity();
        database = FavoriteRoomDatabase.getDatabase(mContext.getApplicationContext());

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDataSetChanged() {
        try {
            FavoriteDao movieDAO = database.favoriteDao();
            mWidgetItems.clear();
            mWidgetItems.addAll(movieDAO.getAllFavoriteMoviesList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        database.close();
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_item);

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(mWidgetItems.get(position).getBackdropPath())
                    .apply(new RequestOptions().fitCenter())
                    .submit(800, 550)
                    .get();

            rv.setImageViewBitmap(R.id.widget_image, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rv.setTextViewText(R.id.widget_title, mWidgetItems.get(position).getTitle());
        Bundle extras = new Bundle();
        extras.putInt(FavMovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
