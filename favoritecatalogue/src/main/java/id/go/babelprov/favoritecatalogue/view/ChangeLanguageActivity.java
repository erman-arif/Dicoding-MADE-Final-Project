package id.go.babelprov.favoritecatalogue.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import id.go.babelprov.favoritecatalogue.MainActivity;
import id.go.babelprov.favoritecatalogue.R;


public class ChangeLanguageActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        // Show Up button and set Title
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.change_language));
        }

        // Initial for localization purpose
        Context mContext = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        // Get current language
        String currentLanguage = preferences.getString("lang", "en");

        // Set default selected Radio Button on current language select
        switch (currentLanguage) {
            case "en" :
                RadioButton radioButtonEn = findViewById(R.id.rd_english);
                radioButtonEn.setChecked(true);
                break;
            case "in" :
                RadioButton radioButtonIn = findViewById(R.id.rd_indonesia);
                radioButtonIn.setChecked(true);
                break;
        }

        Button btnChangeLanguage = findViewById(R.id.btn_change_language);
        btnChangeLanguage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String selectLang = "en";

        RadioGroup rgChangeLanguage = findViewById(R.id.rg_change_language);
        int selectedId = rgChangeLanguage.getCheckedRadioButtonId();

        switch (selectedId) {
            case R.id.rd_english:
                selectLang = "en";
                break;
            case R.id.rd_indonesia:
                selectLang = "in";
                break;
        }

        setLocale(selectLang);
        onBackPressed();
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
    //   Fungsi untuk merubah bahasa
    // --------------------------------------------------------
    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        // Refresh
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();

        preferences.edit().putString("lang", lang).apply();
    }
}
