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

import java.util.Calendar;

/**
 * Created by Oliver on 7/12/2015.
 */
public class WeekTab extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.week_tab,container,false);
        ListView listView = (ListView)(v.findViewById(R.id.week_events));
        listWeekEvents(listView);
        return v;
    }

    private void listWeekEvents(ListView listView){
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
                            EventsContract.EventEntry.COLUMN_NAME_START_DATE + " <= ?";

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH) + 7;

        if(day > 31){
            day -= 31;
            month ++;
        }

        if(month > 12){
            month = 1;
            year ++;
        }

        String monthString = month + "";
        String dayString = day + "";

        if(month<10)
            monthString = "0" + month;

        if(day<10)
            dayString = "0" + day;

        String lastDay = year + monthString + dayString; //7 days from today (usually)
        String[] selectionArgs = {"0", lastDay};

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
