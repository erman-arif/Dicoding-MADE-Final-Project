package id.go.babelprov.favoritecatalogue.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import id.go.babelprov.favoritecatalogue.R;
import id.go.babelprov.favoritecatalogue.helper.UtilHelper;
import id.go.babelprov.favoritecatalogue.model.Favorite;
import id.go.babelprov.favoritecatalogue.model.Movies;
import id.go.babelprov.favoritecatalogue.viewmodel.FavoriteViewModel;


public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_FAVMOVIE = "extra_favmovie";
    private FavoriteViewModel mFavoriteMoviesViewModel;
    private boolean isFavorite = false;
    private Movies movies;


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
        movies = getIntent().getParcelableExtra(EXTRA_FAVMOVIE);

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
    //   Fungsi untuk menghandle click back pada action bar
    // --------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
}
