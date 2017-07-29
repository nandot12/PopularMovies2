package id.co.imastudio.popularmovie.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by idn on 7/29/2017.
 */

public class FilmContract {
    public static final String AUTHORITY = "com.ivanmagda.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "favorite";
    public static final class FilmEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MOVIE;
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "date";
        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildFavoriteMovieUriWithId(int movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }
    }
}
