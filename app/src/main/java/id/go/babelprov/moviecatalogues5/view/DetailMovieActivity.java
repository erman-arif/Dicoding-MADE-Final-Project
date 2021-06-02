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
import id.go.babelprov.moviecatalogues5.model.Movies;
import id.go.babelprov.moviecatalogues5.viewmodel.FavoriteViewModel;


public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private FavoriteViewModel mFavoriteMoviesViewModel;
    private Movies movies;
    private boolean isFavorite = false;
    int drawableResourceId = R.drawable.ic_favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView ivDtlPoster;
        TextView tvDtlTitle, tvDtlRelease, tvDtlGenre, tvDtlPopularity, tvDtlVoteCount, tvDtlOverview;
        String mTitle, mGenre, mDate, mPopularity, mVoteCount, mOverview, mPosterPath;

        // Set Language
        UtilHelper.setLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        final View view = getLayoutInflater().inflate(R.layout.activity_detail_movie, null);

        UtilHelper.showLoading(view, true);

        // Set Back button and title
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_movie));
        }

        ivDtlPoster = findViewById(R.id.iv_dtl_movie_poster);
        tvDtlTitle = findViewById(R.id.tv_dtl_movie_name);
        tvDtlRelease = findViewById(R.id.tv_dtl_movie_releasedate);
        tvDtlGenre = findViewById(R.id.tv_dtl_movie_genre);
        tvDtlPopularity = findViewById(R.id.tv_dtl_movie_popularity);
        tvDtlVoteCount = findViewById(R.id.tv_dtl_movie_votecount);
        tvDtlOverview = findViewById(R.id.tv_dtl_movie_overview);

        // Mendapatkan informasi dari Parcelable
        movies = getIntent().getParcelableExtra(EXTRA_MOVIE);

        mPosterPath = movies.getPosterPath();
        mTitle = movies.getTitle();
        mDate = movies.getReleaseDate();
        mGenre = movies.getGenre();
        mOverview = movies.getOverview();
        mPopularity = Double.toString(movies.getPopularity());
        mVoteCount = Integer.toString(movies.getVoteCount());

        // Mengupdate informasi pada layout detil movie
        Glide.with(this).load(mPosterPath).into(ivDtlPoster);

        tvDtlTitle.setText(mTitle);

        try {
            tvDtlRelease.setText(UtilHelper.getDate(mDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvDtlGenre.setText(mGenre);
        tvDtlPopularity.setText(mPopularity);
        tvDtlVoteCount.setText(mVoteCount);
        tvDtlOverview.setText(mOverview);

        UtilHelper.showLoading(view, false);

        mFavoriteMoviesViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mFavoriteMoviesViewModel.findFavoriteById(movies.getId()).observe(this, new Observer<List<Favorite>>() {
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
                delFavorite(item, movies.getId());
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
                movies.getId(),
                movies.getTitle(),
                movies.getGenre(),
                movies.getPopularity(),
                movies.getVoteCount(),
                movies.getReleaseDate(),
                movies.getOverview(),
                movies.getBackdropPath(),
                movies.getPosterPath(),
                "movie"
        );

        // Insert data to database
        mFavoriteMoviesViewModel.insert(favorite);

        // Set Favorite icon to enable
        item.setIcon(drawableResourceId = R.drawable.ic_favorited);

        // Update Widget
        updateWidget();

        // Information "Favorite Saved"
        Toast.makeText(getApplicationContext(), "Favorite Movie Saved", Toast.LENGTH_LONG).show();
    }

    // --------------------------------------------------------
    //   Fungsi untuk menghapus data Favorite
    // --------------------------------------------------------
    void delFavorite(MenuItem item, Integer id) {

        // Delete data from database
        mFavoriteMoviesViewModel.delete(id);

        // Set Favorite icon to enable
        item.setIcon(drawableResourceId = R.drawable.ic_favorite);

        // Update Widget
        updateWidget();

        // Information "Favorite Saved"
        Toast.makeText(getApplicationContext(), "Favorite Movie Remove", Toast.LENGTH_LONG).show();
    }

    // --------------------------------------------------------
    //    Fungsi untuk menghandle click pada image poster
    //    untuk menampilkan poster dalam ukuran full view
    //    pada Intent ini tidak menggunakan Parcelable, karena
    //    data yg dikirimkan cuman satu sata
    // --------------------------------------------------------
    public void showFullPoster(View v) {
        Intent fullPosterIntent = new Intent(this, FullPosterActivity.class);
        fullPosterIntent.putExtra("poster", movies.getPosterPath());
        startActivity(fullPosterIntent);
    }

    private void updateWidget() {
        // Update Widget
        Intent broadcastIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(broadcastIntent);
    }
}
