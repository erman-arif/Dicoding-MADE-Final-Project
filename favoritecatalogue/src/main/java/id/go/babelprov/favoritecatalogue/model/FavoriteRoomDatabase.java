package id.go.babelprov.favoritecatalogue.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteRoomDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
    private  static FavoriteRoomDatabase INSTANCE;

    public static FavoriteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteRoomDatabase.class, "favorite_table")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new EmptyDbAsync(INSTANCE).execute();
                }
            };

    private static class EmptyDbAsync extends AsyncTask<Void, Void, Void> {

        private final FavoriteDao mDao;

        EmptyDbAsync(FavoriteRoomDatabase db) {
            mDao = db.favoriteDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            return null;
        }
    }
}
