package com.android.arsa.catalogmoviedicoding.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract;
import com.android.arsa.catalogmoviedicoding.data.db.FavoriteHelper;

import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.AUTHORITY;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.CONTENT_URI;

@SuppressWarnings("ConstantConditions")
public class FavoriteProvider extends ContentProvider {

    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);

        mUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE + "/#", MOVIE_ID);
    }

    private FavoriteHelper helper;

    @Override
    public boolean onCreate() {
        helper = new FavoriteHelper(getContext());
        helper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (mUriMatcher.match(uri)) {
            case MOVIE:
                cursor = helper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = helper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long favoriteAdd;

        switch (mUriMatcher.match(uri)) {
            case MOVIE:
                favoriteAdd = helper.insertProvider(values);
                break;
            default:
                favoriteAdd = 0;
                break;
        }

        if (favoriteAdd > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + favoriteAdd);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int favoriteUpdate;

        switch (mUriMatcher.match(uri)) {
            case MOVIE_ID:
                favoriteUpdate = helper.updateProvide(uri.getLastPathSegment(), values);
                break;
            default:
                favoriteUpdate = 0;
                break;
        }
        if (favoriteUpdate > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return favoriteUpdate;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int favoriteDelete;

        switch (mUriMatcher.match(uri)) {
            case MOVIE_ID:
                favoriteDelete = helper.deleteProvide(uri.getLastPathSegment());
                break;
            default:
                favoriteDelete = 0;
                break;
        }
        if (favoriteDelete > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return favoriteDelete;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
