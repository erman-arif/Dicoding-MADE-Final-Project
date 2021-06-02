package id.go.babelprov.favoritecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Favorite implements Parcelable {

    public static final String DATA_MOVIE = "favorite_movie";
    public static final String DATA_TVSHOW = "favorite_tvshow";

    //----------------------------------
    // Properties / Attribute
    //----------------------------------
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "genre")
    private String mGenre;

    @ColumnInfo(name = "popularity")
    private Double mPopularity;

    @ColumnInfo(name = "voteCount")
    private Integer mVoteCount;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "overview")
    private String mOverview;

    @ColumnInfo(name = "backdropPath")
    private String mBackdropPath;

    @ColumnInfo(name = "posterPath")
    private String mPosterPath;

    @ColumnInfo(name = "type")
    private String mType;

    //----------------------------------
    // Constructor
    //----------------------------------
    public Favorite(int id, @NonNull String title, String genre, Double popularity, Integer voteCount,
                    String date, String overview, String backdropPath, String posterPath, String type) {
        this.id = id;
        this.mTitle = title;
        this.mGenre = genre;
        this.mPopularity = popularity;
        this.mVoteCount = voteCount;
        this.mDate = date;
        this.mOverview = overview;
        this.mBackdropPath = backdropPath;
        this.mPosterPath = posterPath;
        this.mType = type;
    }

    //----------------------------------
    // Getter
    //----------------------------------
    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }
    public String getGenre() {
        return mGenre;
    }
    public Double getPopularity() {
        return mPopularity;
    }
    public Integer getVoteCount() {
        return mVoteCount;
    }
    public String getDate() {
        return mDate;
    }
    public String getOverview() { return mOverview; }
    public String getBackdropPath() {
        return mBackdropPath;
    }
    public String getPosterPath() {
        return mPosterPath;
    }
    public String getType() { return mType; }


    //----------------------------------
    // Setter
    //----------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    public void setGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public void setPopularity(Double mPopularity) {
        this.mPopularity = mPopularity;
    }

    public void setVoteCount(Integer mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public void setBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public void setType(String mType) {
        this.mType = mType;
    }


    //----------------------------------
    // Parcelable
    //----------------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mTitle);
        dest.writeString(this.mGenre);
        dest.writeValue(this.mPopularity);
        dest.writeValue(this.mVoteCount);
        dest.writeString(this.mDate);
        dest.writeString(this.mOverview);
        dest.writeString(this.mBackdropPath);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mType);
    }

    public Favorite() {
    }

    protected Favorite(Parcel in) {
        this.id = in.readInt();
        this.mTitle = in.readString();
        this.mGenre = in.readString();
        this.mPopularity = (Double) in.readValue(Double.class.getClassLoader());
        this.mVoteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mDate = in.readString();
        this.mOverview = in.readString();
        this.mBackdropPath = in.readString();
        this.mPosterPath = in.readString();
        this.mType = in.readString();
    }

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
