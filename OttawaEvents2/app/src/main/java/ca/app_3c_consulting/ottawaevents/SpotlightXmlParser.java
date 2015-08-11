package ca.app_3c_consulting.ottawaevents;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Oliver on 7/14/2015.
 */
public class SpotlightXmlParser {
    private static final String ns = null;
    private Calendar c = Calendar.getInstance();
    private EventsDbHelper dbHelper;

    public List parse(InputStream in, EventsDbHelper dbHelper) throws XmlPullParserException, IOException {
        try {
            this.dbHelper = dbHelper;
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List events = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "events");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the event tag
            if (name.equals("event")) {
                if(dbHelper.contains("Spotlight",parser.getAttributeValue(0))){
                    skip(parser);
                }else {
                    Event event = readEvent(parser);
                    if (event != null) {
                        addEventToDB(event);
                        events.add(event);
                    }
                }
            } else {
                skip(parser);
            }
        }
        return events;
    }

    private void addEventToDB(Event event){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EventsContract.EventEntry.COLUMN_NAME_EVENT_NAME, event.getTitleEnglish());
        values.put(EventsContract.EventEntry.COLUMN_NAME_DATABASE, event.getDatabaseName());
        values.put(EventsContract.EventEntry.COLUMN_NAME_EVENT_ID, event.getId());
        values.put(EventsContract.EventEntry.COLUMN_NAME_DESCRIPTION, event.getSummaryEnglish());
        values.put(EventsContract.EventEntry.COLUMN_NAME_START_DATE, event.getStartDate());
        values.put(EventsContract.EventEntry.COLUMN_NAME_END_DATE, event.getEndDate());
        values.put(EventsContract.EventEntry.COLUMN_NAME_LOCATION, event.getLocationEnglish());
        values.put(EventsContract.EventEntry.COLUMN_NAME_WEBSITE, event.getWebsiteURL());
        values.put(EventsContract.EventEntry.COLUMN_NAME_PRICE_INFO, event.getPriceInfoEnglish());
        values.put(EventsContract.EventEntry.COLUMN_NAME_CATEGORY, event.getCategory());
        values.put(EventsContract.EventEntry.COLUMN_NAME_PINNED, 0);
        values.put(EventsContract.EventEntry.COLUMN_NAME_DELETED, 0);

        db.insert(EventsContract.EventEntry.TABLE_NAME,null,values);
        db.close();
    }
    private Event readEvent(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "event");
        Event event = new Event();

        event.setDatabaseName("Spotlight");
        event.setId(parser.getAttributeValue(0));
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("start_date")) {
                event.setStartDate(readText(parser).substring(0,8));
            } else if (name.equals("end_date")) {
                String endDate = readText(parser).substring(0,8);
                int year = Integer.parseInt(endDate.substring(0,4));
                int day = Integer.parseInt(endDate.substring(6,8),10);
                int month = Integer.parseInt(endDate.substring(4,6),10)-1;
                Calendar c2 = Calendar.getInstance();
                c2.set(year, month, day);
                if(c2.before(c)) {
                    skipRemaining(parser);
                    return null;
                }
                event.setEndDate(endDate);
            } else if (name.equals("title_english")) {
                event.setTitleEnglish(readText(parser));
            } else if (name.equals("title_french")) {
                event.setTitleFrench(readText(parser));
            }else if (name.equals("summary_english")) {
                event.setSummaryEnglish(readText(parser));
            }else if (name.equals("summary_french")) {
                event.setSummaryFrench(readText(parser));
            }else if (name.equals("event_times_english")) {
                event.setEventTimesEnglish(readText(parser));
            }else if (name.equals("event_times_french")) {
                event.setEventTimesFrench(readText(parser));
            }else if (name.equals("price_info_english")) {
                event.setPriceInfoEnglish(readText(parser));
            }else if (name.equals("price_info_french")) {
                event.setPriceInfoFrench(readText(parser));
            }else if (name.equals("website_url_english")) {
                event.setWebsiteURL(readText(parser));
            //}else if (name.equals("website_url_french")) {

            }else if (name.equals("locations")) {
                processLocation(parser,event);
            }else if (name.equals("categories")){
                processCategory(parser,event);
            //}else if (name.equals("options")){

            } else{
                skip(parser);
            }
        }
        return event;
    }

    private void processCategory(XmlPullParser parser, Event event) throws IOException, XmlPullParserException{
        parser.next(); //go to first category
        int categoryNum = Integer.parseInt(parser.getAttributeValue(0));
        switch(categoryNum){
            case 0: event.setCategory(Event.CULTURE);
                break;
            case 1: event.setCategory(Event.DANCE_MUSIC_ARTS);
                break;
            case 2: event.setCategory(Event.FAMILY_OUTING);
                break;
            case 3: event.setCategory(Event.DANCE_MUSIC_ARTS);
                break;
            case 4: event.setCategory(Event.DANCE_MUSIC_ARTS);
                break;
            case 5: event.setCategory(Event.CULTURE);
                break;
            case 6: event.setCategory(Event.DANCE_MUSIC_ARTS);
                break;
            case 7: event.setCategory(Event.FAMILY_OUTING);
                break;
            case 8: event.setCategory(Event.DANCE_MUSIC_ARTS);
                break;
            case 9: event.setCategory(Event.DANCE_MUSIC_ARTS);
                break;
            case 10: event.setCategory(Event.FAMILY_OUTING);
                break;
            case 11: event.setCategory(Event.FAMILY_OUTING);
                break;
            case 12: event.setCategory(Event.SPORTS_PLAY);
                break;
            case 13: event.setCategory(Event.FAMILY_OUTING);
                break;
            case 14: event.setCategory(Event.CULTURE);
                break;
            case 15: event.setCategory(Event.CULTURE);
                break;
        }
        skip(parser);
        while(parser.next()!=XmlPullParser.END_TAG){
            skip(parser);
        }

    }

    private void processLocation(XmlPullParser parser, Event event) throws IOException, XmlPullParserException{
        String  locationNameEnglish = "";
        String  locationNameFrench = "";
        String  locationAddressEnglish = "";
        String  locationAddressFrench = "";
        String  city = "";
        parser.next();//go to first location tag
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("name_english")){
                locationNameEnglish = readText(parser);
            }else if(name.equals("name_french")){
                locationNameFrench = readText(parser);
            }else if(name.equals("address_english")){
                locationAddressEnglish = readText(parser);
            }else if(name.equals("address_french")){
                locationAddressFrench = readText(parser);
            //}else if(name.equals("city")){
                //city = readText(parser);
            }else{
                skip(parser);
            }
        }
        parser.next();//skip the locations end tag
        event.setLocationEnglish(locationNameEnglish + " | " + locationAddressEnglish);
        event.setLocationFrench(locationNameFrench + " | " + locationAddressFrench);
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
    private void skipRemaining(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.next();
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 2;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
