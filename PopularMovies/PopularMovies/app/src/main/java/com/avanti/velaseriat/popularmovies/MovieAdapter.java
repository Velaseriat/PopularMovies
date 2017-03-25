package com.avanti.velaseriat.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by velaseriat on 3/17/17.
 */

public class MovieAdapter extends BaseAdapter {
    private MovieEntry[] mData;
    private Context mContext;

    public MovieAdapter(Context context, boolean sortToggle) {
        super();
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData != null ? mData[position] : null;
    }

    @Override
    public long getItemId(int position) {
        return mData != null ? ((MovieEntry) getItem(position)).getId() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.activity_main_movies, null);
            imageView = (ImageView) view.findViewById(R.id.iv_movieImage);
        }
        else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(((MovieEntry) getItem(position)).getImagePath())
                .into(imageView);

        return imageView;
    }

    public void setMovieData(MovieEntry[] movieData) {
        mData = movieData;
        notifyDataSetChanged();
    }


}
