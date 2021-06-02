package id.go.babelprov.moviecatalogues5.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import id.go.babelprov.moviecatalogues5.BuildConfig;
import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.reminder.NotificationReceiver;

public class NotificationSettingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private SwitchCompat swReleaseReminder, swDailyReminder;
    private NotificationReceiver notificationReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        swReleaseReminder = findViewById(R.id.sw_release_reminder);
        swDailyReminder = findViewById(R.id.sw_daily_reminder);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.setting_reminder));
        }

        sharedPreferences = getSharedPreferences(BuildConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        notificationReceiver = new NotificationReceiver(this);

        switchChanged();
        setPreferences();
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
    //   Fungsi untuk menghandle click back Switch Remninder
    // --------------------------------------------------------
    private void switchChanged() {

        swReleaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putBoolean("release_reminder", isChecked);
                sharedPreferencesEditor.apply();

                if (isChecked) {
                    notificationReceiver.setReleaseTodayReminder();
                } else {
                    notificationReceiver.cancelReleaseToday(getApplicationContext());
                }
            }
        });

        swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putBoolean("daily_reminder", isChecked);
                sharedPreferencesEditor.apply();

                if (isChecked) {
                    notificationReceiver.setDailyReminder();
                } else {
                    notificationReceiver.cancelDailyReminder(getApplicationContext());
                }
            }

        });
    }

    // --------------------------------------------------------
    //   Fungsi setPreferences
    // --------------------------------------------------------
    private void setPreferences() {

        boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);

        swReleaseReminder.setChecked(releaseReminder);
        swDailyReminder.setChecked(dailyReminder);
    }
}