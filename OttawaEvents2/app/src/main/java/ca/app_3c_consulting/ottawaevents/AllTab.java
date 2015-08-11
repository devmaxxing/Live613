package ca.app_3c_consulting.ottawaevents;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Oliver on 7/12/2015.
 */
public class AllTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.all_tab,container,false);
        ListView listView = (ListView)(v.findViewById(R.id.all_events));
        listAllEvents(listView);
        return v;
    }

    private void listAllEvents(ListView listView){
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
        String selection = EventsContract.EventEntry.COLUMN_NAME_DELETED + " = ?";
        String[] selectionArgs = {"0"};
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
