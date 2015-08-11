package ca.app_3c_consulting.ottawaevents;

import android.provider.BaseColumns;

/**
 * Created by Oliver on 7/25/2015.
 */
public class EventsContract {
    public EventsContract(){}
    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_NAME_EVENT_NAME = "eventname";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_START_DATE = "startdate";
        public static final String COLUMN_NAME_END_DATE = "enddate";
        public static final String COLUMN_NAME_PRICE_INFO = "priceinfo";
        public static final String COLUMN_NAME_WEBSITE = "website";
        public static final String COLUMN_NAME_EVENT_TIMES = "eventtimes";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_PINNED = "pinned";
        public static final String COLUMN_NAME_DELETED = "deleted";
        public static final String COLUMN_NAME_DATABASE = "database";
        public static final String COLUMN_NAME_EVENT_ID = "eventid";
    }
}
