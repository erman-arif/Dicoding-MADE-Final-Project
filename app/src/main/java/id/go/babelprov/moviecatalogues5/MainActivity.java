package id.go.babelprov.moviecatalogues5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.view.AboutActivity;
import id.go.babelprov.moviecatalogues5.view.ChangeLanguageActivity;
import id.go.babelprov.moviecatalogues5.view.FragmentMovies;
import id.go.babelprov.moviecatalogues5.view.FragmentTvshows;
import id.go.babelprov.moviecatalogues5.view.NotificationSettingActivity;
import id.go.babelprov.moviecatalogues5.view.SearchMoviesActivity;
import id.go.babelprov.moviecatalogues5.view.SearchTvshowsActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment selectedFragment;
    private String titleActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set Language
        UtilHelper.setLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*------------------------------------------------
        | Init Appbar / Toolbar
        +-----------------------------------------------*/
        titleActivity = getString(R.string.title_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*------------------------------------------------
        | Init Navigation Drawer
        +-----------------------------------------------*/
        DrawerLayout drawer = findViewById(R.id.layout_nav_drawer);
        NavigationView navigationView = findViewById(R.id.nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(drwOnNavigationItemSelectedListener);

        /*------------------------------------------------
        | Init Bottom Navigation
        +-----------------------------------------------*/
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(btmOnNavigationItemSelectedListener);

        /*------------------------------------------------
        | Init Title for First Fragment
        +-----------------------------------------------*/
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleActivity);
        }

        /*------------------------------------------------
        | Init Fragment Movies for Active Fragment
        +-----------------------------------------------*/
        if (savedInstanceState == null){
            titleActivity = getString(R.string.title_movie);
            bottomNav.setSelectedItemId(R.id.nav_bottom_movies);
        }
    }

    /*------------------------------------------------
    | Back Button Pressed Event
    +-----------------------------------------------*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.layout_nav_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /*------------------------------------------------
    | ACTION MENU ITEM SELECTED EVENT
    +-----------------------------------------------*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.change_language:
                Intent changeLanguageIntent = new Intent(this, ChangeLanguageActivity.class);
                startActivity(changeLanguageIntent);
                break;
            case R.id.setting_reminder:
                Intent settingReminderIntent = new Intent(this, NotificationSettingActivity.class);
                startActivity(settingReminderIntent);
                break;
            case R.id.action_search_movies:
                Intent searchMoviesIntent = new Intent(this, SearchMoviesActivity.class);
                startActivity(searchMoviesIntent);
                break;
            case R.id.action_search_tvshows:
                Intent searchTvshowsIntent = new Intent(this, SearchTvshowsActivity.class);
                startActivity(searchTvshowsIntent);
                break;
        }

        return true;
    }

    /*------------------------------------------------
    | SET ACTION BAR TITLE
    +-----------------------------------------------*/
    public void setActionBarTitle(String title) {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    /*------------------------------------------------
    | DRAWER NAVIGATION VIEW LISTENER
    +-----------------------------------------------*/
    private NavigationView.OnNavigationItemSelectedListener drwOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_movies:
                    titleActivity = getString(R.string.title_movie);
                    selectedFragment = new FragmentMovies();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    break;
                case R.id.nav_tvshows:
                    titleActivity = getString(R.string.title_tvshow);
                    selectedFragment = new FragmentTvshows();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    break;
                case R.id.nav_about:
                    Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(aboutIntent);
                    break;
                case R.id.nav_change_language:
                    Intent changeLanguageIntent = new Intent(MainActivity.this, ChangeLanguageActivity.class);
                    startActivity(changeLanguageIntent);
                    break;
                case R.id.nav_setting_reminder:
                    Intent settingReminderIntent = new Intent(MainActivity.this, NotificationSettingActivity.class);
                    startActivity(settingReminderIntent);
                    break;
            }

            DrawerLayout drawer = findViewById(R.id.layout_nav_drawer);
            drawer.closeDrawer(GravityCompat.START);

            setActionBarTitle(titleActivity);

            return true;
        }
    };

    /*------------------------------------------------
    | BOTTOM NAVIGATION VIEW LISTENER
    +-----------------------------------------------*/
    private BottomNavigationView.OnNavigationItemSelectedListener btmOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_bottom_movies:
                    titleActivity = getString(R.string.title_movie);
                    selectedFragment = new FragmentMovies();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    break;
                case R.id.nav_bottom_tvshows:
                    titleActivity = getString(R.string.title_tvshow);
                    selectedFragment = new FragmentTvshows();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    break;
                case R.id.nav_bottom_about:
                    Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(aboutIntent);
                    break;
            }
            setActionBarTitle(titleActivity);

            return true;
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

}
