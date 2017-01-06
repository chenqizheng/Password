package com.yonyou.password.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;


/**
 * Created by Chen on 2017/1/6.
 */

public class PasswordProvider extends ContentProvider {

    private static final int URI_MATCHER_PASSWORD_LIST = 1;
    private static final int URI_MATCHER_PASSWORD = 2;

    private PasswordDatabase mPasswordDatabase;


    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(Password.AUTHORITY, "/password/", URI_MATCHER_PASSWORD_LIST);
        URI_MATCHER.addURI(Password.AUTHORITY, "/password/#", URI_MATCHER_PASSWORD);
    }


    @Override
    public boolean onCreate() {
        mPasswordDatabase = new PasswordDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = mPasswordDatabase.getReadableDatabase();
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case URI_MATCHER_PASSWORD_LIST:
                cursor = sqLiteDatabase.query(Password.PasswordTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case URI_MATCHER_PASSWORD:
                SQLiteQueryBuilder sb = new SQLiteQueryBuilder();
                sb.setTables(Password.PasswordTable.TABLE_NAME);
                sb.appendWhere(Password.PasswordTable._ID + "=" + ContentUris.parseId(uri));
                cursor = sb.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqliteDataBase = mPasswordDatabase.getWritableDatabase();
        Uri insertUri = null;
        switch (URI_MATCHER.match(uri)) {
            case URI_MATCHER_PASSWORD_LIST:
                long id = sqliteDataBase.insert(Password.PasswordTable.TABLE_NAME, null, values);
                insertUri = ContentUris.withAppendedId(uri, id);
                break;
            default:
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return insertUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqliteDataBase = mPasswordDatabase.getWritableDatabase();
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case URI_MATCHER_PASSWORD:
                long id = ContentUris.parseId(uri);
                count = sqliteDataBase.delete(Password.PasswordTable.TABLE_NAME, Password.PasswordTable._ID + " = " + id, null);
                break;
            case URI_MATCHER_PASSWORD_LIST:
                count = sqliteDataBase.delete(Password.PasswordTable.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                break;
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase sqliteDatabase = mPasswordDatabase.getWritableDatabase();
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case URI_MATCHER_PASSWORD:
                count = sqliteDatabase.update(Password.PasswordTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCHER_PASSWORD_LIST:
                count = sqliteDatabase.update(Password.PasswordTable.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }


    private class PasswordDatabase extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "password.db";
        private static final int DATABASE_VERSION = 1;

        public PasswordDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public PasswordDatabase(Context context) {
            this(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists " + Password.PasswordTable.TABLE_NAME + "(" +
                    "_id integer primary key AUTOINCREMENT," +
                    "name varchar," +
                    "password varchar)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table " + Password.PasswordTable.TABLE_NAME);
            onCreate(db);
        }
    }

}
