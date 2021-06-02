package id.go.babelprov.moviecatalogues5.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import id.go.babelprov.moviecatalogues5.R;

public class FullPosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_poster);

        // Tampilkan Up button dan Set Title
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Poster");
        }

        ImageView fullScreenPoster = findViewById(R.id.iv_full_poster);
        Intent callActivityIntent = getIntent();

        if(callActivityIntent != null) {
            String poster = callActivityIntent.getStringExtra("poster");

            Glide.with(this)
                    .load(poster)
                    .into(fullScreenPoster);
        }
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
}
