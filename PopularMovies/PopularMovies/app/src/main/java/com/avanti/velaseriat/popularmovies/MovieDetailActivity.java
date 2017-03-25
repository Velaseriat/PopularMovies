package com.avanti.velaseriat.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private MovieEntry mMovieEntry;
    private ImageView mBackgroundImage;
    private LinearLayout mDetailLinearLayout;
    private boolean mDetailToggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieEntry = getIntent().getParcelableExtra(getString(R.string.movie_entry_object_name));

        mDetailLinearLayout = (LinearLayout) findViewById(R.id.ll_details);

        setBackgroundImage();
        setMovieDetailInfo();
    }

    private void setMovieDetailInfo() {
        if (mMovieEntry != null) {
            ((TextView) findViewById(R.id.tv_detail_title))     .setText(mMovieEntry.getTitle());
            ((TextView) findViewById(R.id.tv_detail_release))   .setText(getString(R.string.released_string) + " " + mMovieEntry.getRelease());
            ((TextView) findViewById(R.id.tv_detail_rating))    .setText(mMovieEntry.getRating() + " (" + mMovieEntry.getVotes() + " " + getString(R.string.votes_string) + ")");
            ((TextView) findViewById(R.id.tv_detail_synopsis))  .setText( "\n\n" + mMovieEntry.getSynopsis());
        }

    }

    private void setBackgroundImage() {
        if (mMovieEntry != null) {
            mBackgroundImage = (ImageView) findViewById(R.id.iv_detail_image);
            mBackgroundImage.setOnClickListener(this);
            Picasso.with(this).load(mMovieEntry.getLargeImagePath()).into(mBackgroundImage);
        }
    }

    private void toggleDetailInfo() {
        if (mDetailToggle) {
            mBackgroundImage.setAlpha(1f);
            mDetailLinearLayout.setVisibility(View.INVISIBLE);
        }
        else {
            mBackgroundImage.setAlpha(.33f);
            mDetailLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        mDetailToggle = !mDetailToggle;
        toggleDetailInfo();
    }
}
