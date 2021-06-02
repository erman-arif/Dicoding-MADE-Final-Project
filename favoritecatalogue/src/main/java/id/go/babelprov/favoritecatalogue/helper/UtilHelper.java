package id.go.babelprov.favoritecatalogue.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.go.babelprov.favoritecatalogue.R;
import id.go.babelprov.favoritecatalogue.model.Genres;


public class UtilHelper extends AppCompatActivity {

    public final String lang = "en-US";

    // --------------------------------------------------------
    //   Fungsi untuk merubah bahasa
    // --------------------------------------------------------
    public static void setLocale(Context context) {

        SharedPreferences preferences;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String currentLanguage = preferences.getString("lang", "en");

        Locale myLocale = new Locale(currentLanguage);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    //-------------------------------------------------------------------------------------------
    //   Fungsi untuk menampilkan ProgressBar
    //-------------------------------------------------------------------------------------------
    public static void showLoading(View view, Boolean state) {

        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    //-------------------------------------------------------------------------------
    //   Function for convert genreId to genre name
    //-------------------------------------------------------------------------------
    public static String getGenres(List<Integer> genreIds, ArrayList<Genres> genres) {

        List<String> movieGenres = new ArrayList<>();

        for (Integer genreId : genreIds) {
            for (Genres genre : genres) {
                if (genre.getId() == genreId) {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", movieGenres);
    }

    //-------------------------------------------------------------------------------
    //   Function to convert date to dd-MM-YYYY format
    //-------------------------------------------------------------------------------
    public static String getDate(String releaseDate) throws ParseException {
        try{
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

            return dateFormat.format(date);

        } catch(Exception e){
            return null;
        }
    }

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
