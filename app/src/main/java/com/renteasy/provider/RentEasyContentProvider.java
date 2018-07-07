package com.renteasy.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.renteasy.database.DatabaseContract;
import com.renteasy.database.DatabaseHelper;

/**
 * Created by RUPESH on 8/14/2016.
 */
public class RentEasyContentProvider extends ContentProvider {

    private static DatabaseHelper mOpenHelper;
    static final UriMatcher uriMatcher;
    private static final int ADDRESS = 100;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.BASE_CONTENT_URI.toString();
        uriMatcher.addURI(authority, "address",ADDRESS );

    }

    private int match_uri(Uri uri) {
        String link = uri.toString();
        {
            if (link.contentEquals(DatabaseContract.address_table.buildAddressUri().toString())) {
                return ADDRESS;
            }
            return -1;
        }
    }


    @Override
    public String getType(Uri uri)
    {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ADDRESS:
                return DatabaseContract.address_table.CONTENT_TYPE;

        }
        return  null;
    }
            @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor=null;
        int match = match_uri(uri);
        switch (match) {
            case ADDRESS:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.ADDRESS_TABLE,
                        projection, null, null, null, null, DatabaseContract.address_table._ID +" DESC ");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (match_uri(uri)) {
            case ADDRESS:
                long rowID = db.insert(DatabaseContract.ADDRESS_TABLE, "", values);
                if (rowID > 0)
                {getContext().getContentResolver().notifyChange(uri,null);}
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count=0;
        switch (match_uri(uri)) {
            case ADDRESS:
                count=db.delete(DatabaseContract.ADDRESS_TABLE,selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count=0;
        switch (match_uri(uri)) {
            case ADDRESS:
                count=db.update(DatabaseContract.ADDRESS_TABLE,values,whereClause,whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }
}
