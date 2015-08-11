package ca.app_3c_consulting.ottawaevents;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Oliver on 7/12/2015.
 */
public class PinnedTab extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.pinned_tab,container,false);
        ListView listView = (ListView)(v.findViewById(R.id.pinned_events));
        listPinnedEvents(listView);
        return v;
    }

    private void listPinnedEvents(ListView listView){
        EventsDbHelper dbHelper = new EventsDbHelper(getActivity().getBaseContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                EventsContract.EventEntry._ID,
                EventsContract.EventEntry.COLUMN_NAME_EVENT_NAME,
                EventsContract.EventEntry.COLUMN_NAME_LOCATION,
                EventsContract.EventEntry.COLUMN_NAME_START_DATE,
                EventsContract.EventEntry.COLUMN_NAME_END_DATE,
                EventsContract.EventEntry.COLUMN_NAME_WEBSITE,
                EventsContract.EventEntry.COLUMN_NAME_EVENT_TIMES,
                EventsContract.EventEntry.COLUMN_NAME_DESCRIPTION,
                EventsContract.EventEntry.COLUMN_NAME_PINNED,
                EventsContract.EventEntry.COLUMN_NAME_CATEGORY};
        String selection = EventsContract.EventEntry.COLUMN_NAME_DELETED + " = ? AND " +
                            EventsContract.EventEntry.COLUMN_NAME_PINNED + " = ?";
        String[] selectionArgs = {"0","1"};
        Cursor c = db.query(
                EventsContract.EventEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        EventListCursorAdapter cursorAdapter = new EventListCursorAdapter(getActivity(),c,0);
        listView.setAdapter(cursorAdapter);
    }
}
