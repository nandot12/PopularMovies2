package id.co.imastudio.popularmovie.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.co.imastudio.popularmovie.FilmModel;

/**
 * Created by idn on 7/29/2017.
 */

public class FilmDbHelper extends SQLiteOpenHelper {

    /**
     * Name of the database file.
     */
    private static final String DATABASE_NAME = "movie.db";

    /**
     * Database version.
     */
    private static final int DATABASE_VERSION = 1;

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String NOT_NULL_ATR = " NOT NULL";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
            FilmContract.FilmEntry.TABLE_NAME + " (" +
            FilmContract.FilmEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            FilmContract.FilmEntry.COLUMN_MOVIE_ID + INTEGER_TYPE + NOT_NULL_ATR + COMMA_SEP +
            FilmContract.FilmEntry.COLUMN_OVERVIEW + TEXT_TYPE + NOT_NULL_ATR + COMMA_SEP +
            FilmContract.FilmEntry.COLUMN_POSTER + " BLOB" + COMMA_SEP +
            FilmContract.FilmEntry.COLUMN_POSTER_PATH + TEXT_TYPE + NOT_NULL_ATR + COMMA_SEP +
            FilmContract.FilmEntry.COLUMN_RATING + REAL_TYPE + COMMA_SEP +
            FilmContract.FilmEntry.COLUMN_RELEASE_DATE + TEXT_TYPE + NOT_NULL_ATR + COMMA_SEP +
            FilmContract.FilmEntry.COLUMN_TITLE + TEXT_TYPE + NOT_NULL_ATR + ");";

    private static final String SQL_DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + FilmContract.FilmEntry.TABLE_NAME;

    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DROP_MOVIE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public static ContentValues getMovieContentValues(FilmModel movies) {

        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.COLUMN_MOVIE_ID, movies.getIdFilm());
        values.put(FilmContract.FilmEntry.COLUMN_POSTER, movies.getGambarFilm());
        values.put(FilmContract.FilmEntry.COLUMN_OVERVIEW, movies.getSinopsisFilm());
        values.put(FilmContract.FilmEntry.COLUMN_RELEASE_DATE, movies.getReleaseFilm());
        values.put(FilmContract.FilmEntry.COLUMN_TITLE, movies.getJudulFilm());
        values.put(FilmContract.FilmEntry.COLUMN_POSTER_PATH, movies.getPosterFilm());
        values.put(FilmContract.FilmEntry.COLUMN_RATING, movies.getRatingFilm());

        return values;
    }

}