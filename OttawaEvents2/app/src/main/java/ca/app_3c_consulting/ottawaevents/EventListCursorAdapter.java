package ca.app_3c_consulting.ottawaevents;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Oliver on 8/2/2015.
 */
public class EventListCursorAdapter extends CursorAdapter {

    public EventListCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listitem, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleView = (TextView) view.findViewById(R.id.firstLine);
        TextView dateView = (TextView) view.findViewById(R.id.secondLine);
        TextView locationView = (TextView) view.findViewById(R.id.thirdLine);
        ImageView categoryView = (ImageView) view.findViewById(R.id.icon);
        final ImageView pinnedView = (ImageView) view.findViewById(R.id.pinIcon);
        final int eventID = cursor.getInt(cursor.getColumnIndex(EventsContract.EventEntry._ID));
        final MainActivity activity = (MainActivity) context;
        pinnedView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                int isPinned = 0;
                Log.d("CLICK"," tag: " + pinnedView.getTag() + " grey_id: " + R.drawable.pin_icon_grey);
                if((int)(pinnedView.getTag()) == R.drawable.pin_icon_grey){
                    isPinned = 1;
                    pinnedView.setImageResource(R.drawable.pin_icon_blue);
                    pinnedView.setTag(R.drawable.pin_icon_blue);
                }else{
                    pinnedView.setImageResource(R.drawable.pin_icon_grey);
                    pinnedView.setTag(R.drawable.pin_icon_grey);
                }

                EventsDbHelper dbHelper = new EventsDbHelper(pinnedView.getContext());
                ContentValues values = new ContentValues();
                values.put(EventsContract.EventEntry.COLUMN_NAME_PINNED, isPinned);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String selection = EventsContract.EventEntry._ID + " = ?";
                String[] selectionArgs = { eventID + "" };
                db.update(
                        EventsContract.EventEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                dbHelper.close();
                activity.refresh();
            }
        });

        final String eventName = cursor.getString(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_EVENT_NAME));

        final String eventLocation = cursor.getString(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_LOCATION));

        final String eventTimes = cursor.getString(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_EVENT_TIMES));

        final String eventDescription = cursor.getString(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_DESCRIPTION));

        final String eventWebsite = cursor.getString(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_WEBSITE));

        String startDate = cursor.getString(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_START_DATE));
        String endDate = cursor.getString(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_END_DATE));
        final String dates = formatDate(startDate) + " - " + formatDate(endDate);

        int pinned = cursor.getInt(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_PINNED));

        int category = cursor.getInt(
                cursor.getColumnIndex(EventsContract.EventEntry.COLUMN_NAME_CATEGORY));

        //cursor.close();

        pinnedView.setTag(R.drawable.pin_icon_grey);
        pinnedView.setImageResource(R.drawable.pin_icon_grey);
        if(pinned == 1) {
            pinnedView.setImageResource(R.drawable.pin_icon_blue);
            pinnedView.setTag(R.drawable.pin_icon_blue);
        }

        switch(category){
            case Event.CULTURE: categoryView.setImageResource(R.drawable.culture2);
                break;
            case Event.DANCE_MUSIC_ARTS: categoryView.setImageResource(R.drawable.arts2);
                break;
            case Event.FAMILY_OUTING: categoryView.setImageResource(R.drawable.family2);
                break;
            case Event.FRIENDLY_HANGOUTS: categoryView.setImageResource(R.drawable.family2);
                break;
            case Event.NATURE_EXPLORATION: categoryView.setImageResource(R.drawable.nature2);
                break;
            case Event.NIGHTLIFE: categoryView.setImageResource(R.drawable.night_life2);
                break;
            case Event.SEASONAL_CELEBRATIONS: categoryView.setImageResource(R.drawable.nature2);
                break;
            case Event.SPORTS_PLAY: categoryView.setImageResource(R.drawable.sports2);
                break;
        }
        titleView.setText(eventName);
        dateView.setText(dates);
        locationView.setText(eventLocation);

        view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(activity, EventDetailsActivity.class);
                i.putExtra("name",eventName);
                i.putExtra("date",dates);
                i.putExtra("time",eventTimes);
                i.putExtra("location",eventLocation);
                i.putExtra("website",eventWebsite);
                i.putExtra("description",eventDescription);
                activity.startActivity(i);
            }
        });
    }

    private String formatDate(String spotlightDate){
        int year = Integer.parseInt(spotlightDate.substring(0,4));
        int day = Integer.parseInt(spotlightDate.substring(6,8),10);
        String month = "";
        switch(Integer.parseInt(spotlightDate.substring(4,6),10)){
            case 1: month = "Jan.";
                break;
            case 2: month = "Feb.";
                break;
            case 3: month = "Mar.";
                break;
            case 4: month = "Apr.";
                break;
            case 5: month = "May";
                break;
            case 6: month = "June";
                break;
            case 7: month = "July";
                break;
            case 8: month = "Aug.";
                break;
            case 9: month = "Sept.";
                break;
            case 10: month = "Oct.";
                break;
            case 11: month = "Nov.";
                break;
            case 12: month = "Dec.";
                break;
        }
        return month + " " + day + ", " + year;
    }
}
