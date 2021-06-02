package id.go.babelprov.moviecatalogues5.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.go.babelprov.moviecatalogues5.BuildConfig;
import id.go.babelprov.moviecatalogues5.MainActivity;
import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.model.Movies;
import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.networking.ApiServer;
import id.go.babelprov.moviecatalogues5.networking.MovieInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.babelprov.moviecatalogues5.helper.UtilHelper.lang;


public class NotificationReceiver extends BroadcastReceiver {

	private Context context;
	private static final String EXTRA_TYPE = "type";
	private static final String TYPE_DAILY = "daily_reminder";
	private static final String TYPE_RELEASE = "release_reminder";
	private static final int ID_DAILY_REMINDER = 1000;
	private static final int ID_RELEASE_TODAY = 1001;

	// --------------------------------------------------------
	//   Contructor
	// --------------------------------------------------------
	public NotificationReceiver(){}
	public NotificationReceiver(Context context) {
		this.context = context;
	}

	// --------------------------------------------------------
	//   Methode onReceive
	// --------------------------------------------------------
	@Override
	public void onReceive(Context context, Intent intent) {

		String type = intent.getStringExtra(EXTRA_TYPE);

		if (type.equals(TYPE_DAILY)) {
			getDailyReminder(context);
		}
		else if (type.equals(TYPE_RELEASE)) {
			getReleaseToday(context);
		}
	}

	// --------------------------------------------------------
	//   Methode Set Daily Reminder
	// --------------------------------------------------------
	public void setDailyReminder() {

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		setReminder(alarmManager, pendingIntent, TYPE_DAILY);
	}

	// --------------------------------------------------------
	//   Methode Set Release Today
	// --------------------------------------------------------
	public void setReleaseTodayReminder() {

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, getReminderIntent(TYPE_RELEASE), 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		setReminder(alarmManager, pendingIntent, TYPE_RELEASE);
	}


	public void setReminder(AlarmManager alarmManager, PendingIntent pendingIntent, String type) {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
		{
			alarmManager.set(AlarmManager.RTC_WAKEUP, getReminderTime(type).getTimeInMillis(), pendingIntent);
		}
		else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
		{
			alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(type).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
		}
		else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getReminderTime(type).getTimeInMillis(), pendingIntent);
		}
	}

	// --------------------------------------------------------
	//   Methode Get Reminder Time
	//   in set at 7:00 AM for daily reminder for Check apps
	//   in set at 8:00 AM for daily release movies reminder
	// --------------------------------------------------------
	private Calendar getReminderTime(String type) {

		Calendar calendar = Calendar.getInstance();

		int hour = type.equals(TYPE_RELEASE) ? 10 : 11;

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 34);
		calendar.set(Calendar.SECOND, 0);

		if (calendar.before(Calendar.getInstance())) {
			calendar.add(Calendar.DATE, 1);
		}

		return calendar;
	}

	// --------------------------------------------------------
	//   Methode Get Release Today
	// --------------------------------------------------------
	private Intent getReminderIntent(String type) {
		Intent intent = new Intent(context, NotificationReceiver.class);
		intent.putExtra(EXTRA_TYPE, type);
		return intent;
	}

	// --------------------------------------------------------
	//   Methode Get daily Reminder to set data for
	//   notification daily reminder
	// --------------------------------------------------------
	private void getDailyReminder(final Context context) {

		String title = context.getResources().getString(R.string.app_name);
		String desc = context.getResources().getString(R.string.daily_reminder);
		int NOTIFICATION_ID = 1;

		showDailyReminder(context, title, desc, NOTIFICATION_ID);
	}

	// --------------------------------------------------------
	//   Methode Get release today to set data for
	//   notification release today
	// --------------------------------------------------------
	private void getReleaseToday(final Context context) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		final String now = dateFormat.format(date);

		MovieInterface movieInterface = ApiServer.getService();
		Call<MoviesResponse> call = movieInterface.getReleaseTodayMovies(BuildConfig.TMDB_API_KEY, lang, now, now);
		call.enqueue(new Callback<MoviesResponse>() {
			@Override
			public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
				if (response.isSuccessful()) {
					List<Movies> movies = response.body().getResults();
					int id = 2;
					for (Movies movie : movies) {
						String title = movie.getTitle();
						String desc = title + " " + context.getString(R.string.release_reminder);
						String icon = movie.getTitle();
						showReleaseToday(context, title, desc, icon, id);
						id++;
					}
				}
			}

			@Override
			public void onFailure(Call<MoviesResponse> call, Throwable t) {

			}
		});
	}

	// ---------------------------------------------------------------
	//   Methode to show notofication for daily reminder to open
	//   Movies Catalogue Apps
	// ---------------------------------------------------------------
	private void showDailyReminder(Context context, String title, String desc, int id) {

		String CHANNEL_ID = "Channel_1";
		String CHANNEL_NAME = "Daily Reminder channel";

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_menu_movies)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_movies))
				.setContentTitle(title)
				.setContentText(desc)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setSound(uriRingtone)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		Notification notification = mBuilder.build();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
			mBuilder.setChannelId(CHANNEL_ID);

			if (mNotificationManager != null) {
				mNotificationManager.createNotificationChannel(channel);
			}
		}

		if (mNotificationManager != null) {
			mNotificationManager.notify(id, notification);
		}
	}

	// ---------------------------------------------------------------
	//   Methode to show notofication for daily movies release today
	// ---------------------------------------------------------------
	private void showReleaseToday(Context context, String title, String desc, String icon, int id) {

		String CHANNEL_ID = "Channel_2";
		String CHANNEL_NAME = "Today release channel";

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_menu_movies)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_movies))
				.setContentTitle(title)
				.setContentText(desc)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setSound(uriRingtone)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		Notification notification = mBuilder.build();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
			mBuilder.setChannelId(CHANNEL_ID);

			if (mNotificationManager != null) {
				mNotificationManager.createNotificationChannel(channel);
			}
		}

		if (mNotificationManager != null) {
			mNotificationManager.notify(id, notification);
		}
	}

	// ---------------------------------------------------------------
	//   Methode to cancel Daily Reminder Alarm
	// ---------------------------------------------------------------
	public void cancelDailyReminder(Context context) {

		cancelReminder(context, TYPE_DAILY);
	}

	// ---------------------------------------------------------------
	//   Methode to cancel Release Today Alarm
	// ---------------------------------------------------------------
	public void cancelReleaseToday(Context context) {

		cancelReminder(context, TYPE_RELEASE);
	}

	// ---------------------------------------------------------------
	//   Methode to cancel Alarm Manager for reminder
	// ---------------------------------------------------------------
	private void cancelReminder(Context context, String type) {

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, NotificationReceiver.class);
		int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_REMINDER : ID_RELEASE_TODAY;
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
		pendingIntent.cancel();

		if (alarmManager != null) {
			alarmManager.cancel(pendingIntent);
		}
	}
}
