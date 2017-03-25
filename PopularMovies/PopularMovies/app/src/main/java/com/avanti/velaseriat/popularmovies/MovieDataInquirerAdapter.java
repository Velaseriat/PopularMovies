package com.avanti.velaseriat.popularmovies;

import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by velaseriat on 3/19/17.
 */

public class MovieDataInquirerAdapter implements MovieDataInquirer {

    private static String API_KEY;

    private static final String MOVIE_URL_BASE      = "https://api.themoviedb.org/3/movie";

    private static final String POPULAR_KEY_PATH    = "popular";
    private static final String TOP_RATED_KEY_PATH  = "top_rated";

    private static final String IMG_URL_BASE        = "http://image.tmdb.org/t/p";

    private static final String SMALL_IMG_PATH      = "w185";
    private static final String LARGE_IMG_PATH      = "w780";

    private static final String API_KEY_PARAM       = "api_key";

    //I noticed there were pages.
    private static final String PAGE_PARAM          = "page";

    public MovieDataInquirerAdapter() {
        API_KEY = getApiKeyFromFile();
    }

    @Override
    public MovieEntry[] getMovieList(int page, boolean sortToggle) {
        MovieEntry[] mMoviesList = new MovieEntry[0];

        //Build the query here
        //https://api.themoviedb.org/3/movie/157336?api_key={api_key}
        Uri uri = Uri.parse(MOVIE_URL_BASE).buildUpon()
                .appendPath(sortToggle ? POPULAR_KEY_PATH : TOP_RATED_KEY_PATH)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        String response = getJSONResponse(uri);

        JSONObject jsonObject = null;
        if ( response != null ) {
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Package result and send it
        //do these strings really need to be extracted to string.xml? These are more for code than anything...
        if ( jsonObject != null ) {
            try {
                JSONArray movies = jsonObject.getJSONArray("results");
                mMoviesList = new MovieEntry[movies.length()];
                for ( int i = 0; i < movies.length(); i++) {
                    JSONObject js = movies.getJSONObject(i);

                    MovieEntry me = new MovieEntry(
                            js.getInt("id"),
                            js.getString("title"),
                            js.getDouble("vote_average"),
                            getSmallImage(js.getString("poster_path")),
                            getLargeImage(js.getString("poster_path")),
                            js.getString("release_date"),
                            js.getString("overview"),
                            js.getInt("vote_count")
                    );
                    mMoviesList[i] = me;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mMoviesList;
    }

    //I guess I was thinking that if I clicked on a web URL and i had to launch the details directly, the id would be in the URL and this would get called instead.
    @Override
    public MovieEntry getMovieDetails(int id) {
        Uri uri = Uri.parse(MOVIE_URL_BASE).buildUpon()
                .appendPath(String.valueOf(id))
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        String response = getJSONResponse(uri);

        JSONObject jsonObject = null;
        if ( response != null ) {
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        MovieEntry movieEntry = null;
        try {
            movieEntry = new MovieEntry(
                    id,
                    jsonObject.getString("title"),
                    jsonObject.getDouble("vote_average"),
                    getSmallImage(jsonObject.getString("poster_path")),
                    getLargeImage(jsonObject.getString("poster_path")),
                    jsonObject.getString("release_date"),
                    jsonObject.getString("overview"),
                    jsonObject.getInt("vote_count")
            );

            return movieEntry;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJSONResponse(Uri uri) {
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Get JSON response here
        String response = null;
        try {
            response = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getSmallImage(String poster_path) {
        return buildImageUri(poster_path, SMALL_IMG_PATH);
    }

    private String getLargeImage(String poster_path) {
        return buildImageUri(poster_path, LARGE_IMG_PATH);
    }

    private String buildImageUri(String posterPath, String imgSize) {
        Uri uri = Uri.parse(IMG_URL_BASE).buildUpon()
                .appendPath(imgSize)
                .appendPath(posterPath.replaceFirst("\\/", ""))
                .build();
        return uri.toString();
    }

    //This came directly from https://github.com/Velaseriat/ud851-Sunshine/blob/student/S04.03-Solution-AddMapAndSharing/app/src/main/java/com/example/android/sunshine/utilities/NetworkUtils.java
    //I understand everything that is going on here
    private static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    //please put a string representation of your api key here
    private String getApiKeyFromFile() {
        //will change this later
        return "";
    }
}
