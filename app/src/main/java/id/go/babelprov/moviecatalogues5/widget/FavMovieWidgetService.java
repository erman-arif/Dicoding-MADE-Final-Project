package id.go.babelprov.moviecatalogues5.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavMovieWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
