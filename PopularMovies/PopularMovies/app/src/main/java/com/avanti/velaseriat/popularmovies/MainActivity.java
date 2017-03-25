package com.avanti.velaseriat.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private boolean mSortToggle = true;

    //If i ever wanted to add page functionality to obtain more results in the future
    private int mPageNum = 1;

    private GridView mGridView;
    private MovieAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesAdapter = new MovieAdapter(this, mSortToggle);
        mGridView = (GridView) findViewById(R.id.gv_movies_list);
        mGridView.setOnItemClickListener(this);
        mGridView.setAdapter(mMoviesAdapter);

        loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                setSortToggle(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSortToggle(MenuItem item) {
        mSortToggle = !mSortToggle;
        item.setTitle(mSortToggle ? R.string.sort_rating : R.string.sort_popular);
        loadMovies();
    }

    public void loadMovies(){
        new FetchMovieData(new MovieDataInquirerAdapter()).execute(mSortToggle);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(getString(R.string.movie_entry_object_name), (Parcelable) mMoviesAdapter.getItem(position));

        startActivity(intent);
    }

    private class FetchMovieData extends AsyncTask<Boolean, Void, MovieEntry[]> {

        MovieDataInquirer mMovieDataInquirer;

        public FetchMovieData(MovieDataInquirer movieDataInquirer) {
            mMovieDataInquirer = movieDataInquirer;
        }

        @Override
        protected MovieEntry[] doInBackground(Boolean... params) {
            return mMovieDataInquirer.getMovieList(mPageNum, params[0]);
        }

        @Override
        protected void onPostExecute(MovieEntry[] movieEntries) {
            if (mMoviesAdapter != null) {
                mMoviesAdapter.setMovieData(movieEntries);
            }
        }
    }
}
