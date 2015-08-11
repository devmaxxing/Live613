package ca.app_3c_consulting.ottawaevents;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ca.app_3c_consulting.ottawaevents.EventsContract.EventEntry;

/**
 * Created by Oliver on 7/26/2015.
 */
public class EventsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Events.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EventEntry.TABLE_NAME + " (" +
                    EventEntry._ID + " INTEGER PRIMARY KEY," +
                    EventEntry.COLUMN_NAME_EVENT_NAME + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_EVENT_TIMES + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_PRICE_INFO + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_WEBSITE + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_CATEGORY + INTEGER_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_PINNED + INTEGER_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_DELETED + INTEGER_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_DATABASE + INTEGER_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_EVENT_ID + INTEGER_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventEntry.TABLE_NAME;

    public EventsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean contains(String databaseName, String eventID){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EventEntry.COLUMN_NAME_EVENT_ID};
        String selection = EventEntry.COLUMN_NAME_DATABASE + " = ? AND " +
                                EventEntry.COLUMN_NAME_EVENT_ID + " = ?";
        String[] selectionArgs = {databaseName, eventID};
        Cursor c = db.query(
                EventEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if(c.getCount() == 1){
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    public boolean contains(String databaseName, String eventName, String startDate){
        return false;
    }
}
