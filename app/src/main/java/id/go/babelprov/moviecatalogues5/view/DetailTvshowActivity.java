package id.go.babelprov.moviecatalogues5.view;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.model.Tvshows;
import id.go.babelprov.moviecatalogues5.viewmodel.FavoriteViewModel;


public class DetailTvshowActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private FavoriteViewModel mFavoriteTvshowsViewModel;
    private Tvshows tvshows;
    private boolean isFavorite = false;
    int drawableResourceId = R.drawable.ic_favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView ivDtlPoster;
        TextView tvDtlName, tvDtlAirDate, tvDtlGenre, tvDtlPopularity, tvDtlVoteCount, tvDtlOverview;
        String mTitle, mGenre, mDate, mPopularity, mVoteCount, mOverview, mPosterPath;

        // Set Language
        UtilHelper.setLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        final View view = getLayoutInflater().inflate(R.layout.activity_detail_movie, null);

        UtilHelper.showLoading(view, true);

        // Set Back button and title
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_tvshow));
        }

        ivDtlPoster = findViewById(R.id.iv_dtl_tvshow_poster);
        tvDtlName = findViewById(R.id.tv_dtl_tvshow_name);
        tvDtlAirDate = findViewById(R.id.tv_dtl_tvshow_airdate);
        tvDtlGenre = findViewById(R.id.tv_dtl_tvshow_genre);
        tvDtlPopularity = findViewById(R.id.tv_dtl_tvshow_popularity);
        tvDtlVoteCount = findViewById(R.id.tv_dtl_tvshow_votecount);
        tvDtlOverview = findViewById(R.id.tv_dtl_tvshow_overview);

        // Mendapatkan informasi dari Parcelable
        tvshows = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        mPosterPath = tvshows.getPosterPath();
        mTitle = tvshows.getName();
        mDate = tvshows.getFirstAirDate();
        mGenre = tvshows.getGenre();
        mOverview = tvshows.getOverview();
        mPopularity = Double.toString(tvshows.getPopularity());
        mVoteCount = Integer.toString(tvshows.getVoteCount());

        // Mengupdate informasi pada layout detil tvshow
        Glide.with(this).load(mPosterPath).into(ivDtlPoster);

        tvDtlName.setText(mTitle);

        try {
            tvDtlAirDate.setText(UtilHelper.getDate(mDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvDtlGenre.setText(mGenre);
        tvDtlPopularity.setText(mPopularity);
        tvDtlVoteCount.setText(mVoteCount);
        tvDtlOverview.setText(mOverview);

        UtilHelper.showLoading(view, false);

        mFavoriteTvshowsViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mFavoriteTvshowsViewModel.findFavoriteById(tvshows.getId()).observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                isFavorite = !(favorites == null || favorites.isEmpty());
            }
        });
    }

    // --------------------------------------------------------
    //   Action Menu Add Favorite
    // --------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);

        if (isFavorite) {
            menu.findItem(R.id.mn_favorite).setIcon(drawableResourceId = R.drawable.ic_favorited);
        } else {
            menu.findItem(R.id.mn_favorite).setIcon(drawableResourceId = R.drawable.ic_favorite);
        }

        return true;
    }

    // --------------------------------------------------------
    //   Fungsi untuk menghandle click back pada action bar
    // --------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.mn_favorite) {

            if(isFavorite) {
                delFavorite(item, tvshows.getId());
            } else {
                addFavorite(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // --------------------------------------------------------
    //   Fungsi untuk menambah data Favorite
    // --------------------------------------------------------
    void addFavorite(MenuItem item) {

        // Setup data to store
        Favorite favorite = new Favorite(
                tvshows.getId(),
                tvshows.getName(),
                tvshows.getGenre(),
                tvshows.getPopularity(),
                tvshows.getVoteCount(),
                tvshows.getFirstAirDate(),
                tvshows.getOverview(),
                tvshows.getBackdropPath(),
                tvshows.getPosterPath(),
                "tvshow"
        );

        // Insert data to database
        mFavoriteTvshowsViewModel.insert(favorite);

        // Set Favorite icon to enable
        item.setIcon(drawableResourceId = R.drawable.ic_favorited);

        // Update Widget
        updateWidget();

        // Information "Favorite Saved"
        Toast.makeText(getApplicationContext(), "Favorite TV Show Saved", Toast.LENGTH_LONG).show();
    }

    // --------------------------------------------------------
    //   Fungsi untuk menghapus data Favorite
    // --------------------------------------------------------
    void delFavorite(MenuItem item, Integer id) {

        // Delete data from database
        mFavoriteTvshowsViewModel.delete(id);

        // Set Favorite icon to enable
        item.setIcon(drawableResourceId = R.drawable.ic_favorite);

        // Update Widget
        updateWidget();

        // Information "Favorite Saved"
        Toast.makeText(getApplicationContext(), "Favorite TV Show Remove", Toast.LENGTH_LONG).show();
    }

    // --------------------------------------------------------
    //    Fungsi untuk menghandle click pada image poster
    //    untuk menampilkan poster dalam ukuran full view
    //    pada Intent ini tidak menggunakan Parcelable, karena
    //    data yg dikirimkan cuman satu sata
    // --------------------------------------------------------
    public void showFullPoster(View v) {
        Intent fullPosterIntent = new Intent(this, FullPosterActivity.class);
        fullPosterIntent.putExtra("poster", tvshows.getPosterPath());
        startActivity(fullPosterIntent);
    }

    private void updateWidget() {
        // Update Widget
        Intent broadcastIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(broadcastIntent);
    }
}
