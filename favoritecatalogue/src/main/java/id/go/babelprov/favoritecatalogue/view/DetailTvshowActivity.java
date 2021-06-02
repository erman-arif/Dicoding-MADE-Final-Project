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
import id.go.babelprov.favoritecatalogue.viewmodel.FavoriteViewModel;

public class DetailTvshowActivity extends AppCompatActivity {

    public static final String EXTRA_FAVTVSHOW = "extra_tvshow";
    private FavoriteViewModel mFavoriteTvshowsViewModel;
    private boolean isFavorite = false;
    private Favorite favTvshows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView ivDtlPoster;
        TextView tvDtlTitle, tvDtlRelease, tvDtlGenre, tvDtlPopularity, tvDtlVoteCount, tvDtlOverview;
        String mTitle, mGenre, mDate, mPopularity, mVoteCount, mOverview, mPosterPath;

        // Set Language
        UtilHelper.setLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        final View view = getLayoutInflater().inflate(R.layout.activity_detail_tvshow, null);

        UtilHelper.showLoading(view, true);

        // Set Back button and title
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_tvshow));
        }

        ivDtlPoster = findViewById(R.id.iv_dtl_tvshow_poster);
        tvDtlTitle = findViewById(R.id.tv_dtl_tvshow_name);
        tvDtlRelease = findViewById(R.id.tv_dtl_tvshow_airdate);
        tvDtlGenre = findViewById(R.id.tv_dtl_tvshow_genre);
        tvDtlPopularity = findViewById(R.id.tv_dtl_tvshow_popularity);
        tvDtlVoteCount = findViewById(R.id.tv_dtl_tvshow_votecount);
        tvDtlOverview = findViewById(R.id.tv_dtl_tvshow_overview);

        // Mendapatkan informasi dari Parcelable
        favTvshows = getIntent().getParcelableExtra(EXTRA_FAVTVSHOW);

        mPosterPath = favTvshows.getPosterPath();
        mTitle = favTvshows.getTitle();
        mDate = favTvshows.getDate();
        mGenre = favTvshows.getGenre();
        mOverview = favTvshows.getOverview();
        mPopularity = Double.toString(favTvshows.getPopularity());
        mVoteCount = Integer.toString(favTvshows.getVoteCount());

        // Mengupdate informasi pada layout detil tv shows
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

        mFavoriteTvshowsViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mFavoriteTvshowsViewModel.findFavoriteById(favTvshows.getId()).observe(this, new Observer<List<Favorite>>() {
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
        fullPosterIntent.putExtra("poster", favTvshows.getPosterPath());
        startActivity(fullPosterIntent);
    }
}
