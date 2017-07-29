package id.co.imastudio.popularmovie.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by idn on 7/29/2017.
 */

public class FilmProvider extends ContentProvider {

    public static final String LOG_TAG = FilmProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the movies table.
     */
    private static final int MOVIES = 100;

    /**
     * URI matcher code for the content URI for a single movie in the movies table.
     */
    private static final int MOVIE_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FilmContract.AUTHORITY, FilmContract.PATH_MOVIE, MOVIES);
        sUriMatcher.addURI(FilmContract.AUTHORITY, FilmContract.PATH_MOVIE + "/#", MOVIE_ID);
    }

    /**
     * Database helper object.
     */
    private FilmDbHelper mFilmDbHelper;

    @Override
    public boolean onCreate() {
        mFilmDbHelper = new FilmDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mFilmDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                cursor = database.query(FilmContract.FilmEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MOVIE_ID:
                selection = FilmContract.FilmEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{idStringFrom(uri)};
                cursor = database.query(FilmContract.FilmEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        Context context = getContext();
        if (context != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return FilmContract.FilmEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return FilmContract.FilmEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                SQLiteDatabase database = mFilmDbHelper.getWritableDatabase();

                long id = database.insert(FilmContract.FilmEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }

                // Notify all listeners that the data has changed for the product content URI.
                notifyChangeWithUri(uri);

                // Return the new URI with the ID (of the newly inserted row) appended at the end.
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mFilmDbHelper.getWritableDatabase();

        int rowsDeleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                // Delete a single row given by the ID in the URI.
                selection = FilmContract.FilmEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{idStringFrom(uri)};
                rowsDeleted = database.delete(FilmContract.FilmEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed.
        if (rowsDeleted != 0) {
            notifyChangeWithUri(uri);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                selection = FilmContract.FilmEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{idStringFrom(uri)};

                SQLiteDatabase database = mFilmDbHelper.getWritableDatabase();

                int rowsUpdated = database.update(FilmContract.FilmEntry.TABLE_NAME, values, selection, selectionArgs);
                if (rowsUpdated != 0) {
                    notifyChangeWithUri(uri);
                }

                return rowsUpdated;
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private String idStringFrom(Uri uri) {
        return String.valueOf(ContentUris.parseId(uri));
    }

    private void notifyChangeWithUri(Uri uri) {
        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }
    }

}
