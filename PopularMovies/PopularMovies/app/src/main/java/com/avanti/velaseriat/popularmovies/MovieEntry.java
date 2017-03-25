package com.avanti.velaseriat.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by velaseriat on 3/19/17.
 */

public class MovieEntry implements Parcelable{
    private int     mId;
    private String  mTitle;
    private double  mRating;
    private String  mImagePath;
    private String  mLargeImagePath;
    private String  mRelease;
    private String  mSynopsis;
    private int     mVotes;

    public MovieEntry(int id, String title, double rating, String imagePath, String largeImagePath, String release, String synopsis, int votes) {
        mId                 = id;
        mTitle              = title;
        mRating             = rating;
        mImagePath          = imagePath;
        mLargeImagePath     = largeImagePath;
        mRelease            = release;
        mSynopsis           = synopsis;
        mVotes              = votes;
    }

    private MovieEntry(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mRating = in.readDouble();
        mImagePath = in.readString();
        mLargeImagePath = in.readString();
        mRelease = in.readString();
        mSynopsis = in.readString();
        mVotes = in.readInt();
    }

    public static final Creator<MovieEntry> CREATOR = new Creator<MovieEntry>() {
        @Override
        public MovieEntry createFromParcel(Parcel in) {
            return new MovieEntry(in);
        }

        @Override
        public MovieEntry[] newArray(int size) {
            return new MovieEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeDouble(mRating);
        dest.writeString(mImagePath);
        dest.writeString(mLargeImagePath);
        dest.writeString(mRelease);
        dest.writeString(mSynopsis);
        dest.writeInt(mVotes);
    }

    public int getId() {
        return mId;
    }

    public String getRelease() {
        return mRelease;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public int getVotes() {
        return mVotes;
    }

    public String getTitle() {
        return mTitle;
    }

    public double getRating() {
        return mRating;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public String getLargeImagePath() {
        return mLargeImagePath;
    }

    public String toString() {
        return mTitle;
    }
}
