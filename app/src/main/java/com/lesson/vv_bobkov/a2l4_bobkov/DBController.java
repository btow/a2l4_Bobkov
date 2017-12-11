package com.lesson.vv_bobkov.a2l4_bobkov;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lesson.vv_bobkov.a2l4_bobkov.Exceptions.DBCursorIsNullExceptions;
import com.lesson.vv_bobkov.a2l4_bobkov.Exceptions.DBNewVersionLessOldExceptions;

/**
 * Created by bobkov-vv on 11.12.2017.
 */

class DBController {

    private final String DB_NAME = "notes";
    private final int DB_VERSION = 1;
    private Context mCxt;
    private DbOpenHelper mDbOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    DBController(Context cxt) {
        mCxt = cxt;
        mDbOpenHelper = new DbOpenHelper(mCxt, DB_NAME, null, DB_VERSION);
        mSqLiteDatabase = mDbOpenHelper.getWritableDatabase();
    }

    public SQLiteDatabase getmSqLiteDatabase() {
        return mSqLiteDatabase;
    }

    public void readeNoteWithTitleArrayListFromBd()
            throws DBCursorIsNullExceptions, DBCursorIsEmpty {
        App.getmApp().setmNoteWithTitleList(NotesTable.createNoteWithTitleArrayListFromBd(mSqLiteDatabase));
    }

    private class DbOpenHelper extends SQLiteOpenHelper {

        public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            NotesTable.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                NotesTable.upgrade(db, oldVersion, newVersion);
            } catch (DBNewVersionLessOldExceptions e) {
                e.printStackTrace();
            }
        }
    }
}
