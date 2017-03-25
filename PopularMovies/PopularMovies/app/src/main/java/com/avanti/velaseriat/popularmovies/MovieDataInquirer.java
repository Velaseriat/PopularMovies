package com.avanti.velaseriat.popularmovies;

/**
 * Created by velaseriat on 3/19/17.
 */

public interface MovieDataInquirer {

    MovieEntry[] getMovieList(int page, boolean sortToggle);

    MovieEntry getMovieDetails(int id);
}
