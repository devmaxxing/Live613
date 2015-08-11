package ca.app_3c_consulting.ottawaevents;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EventDetailsActivity extends Activity {
    String eventName, eventDates, eventTimes, eventLocation, eventWebsite, eventDescription;
    TextView nameView, dateView, timeView, locationView, websiteView, descriptionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Bundle extras = getIntent().getExtras();
        eventName = extras.getString("name");
        eventDates = extras.getString("date");
        eventTimes = extras.getString("time");
        eventLocation = extras.getString("location");
        eventWebsite = extras.getString("website");
        eventDescription = extras.getString("description");

        nameView = (TextView) findViewById(R.id.event_name);
        dateView = (TextView) findViewById(R.id.event_date);
        timeView = (TextView) findViewById(R.id.event_times);
        locationView = (TextView) findViewById(R.id.event_location);
        websiteView = (TextView) findViewById(R.id.event_website);
        descriptionView = (TextView) findViewById(R.id.event_description);

        nameView.setText(eventName);
        fillTextView(dateView, "<b>Date: </b>" , eventDates);
        fillTextView(timeView, "<b>Times: </b>" , eventTimes);
        fillTextView(locationView, "<b>Location: </b>", eventLocation);
        fillTextView(websiteView, "<b>Website: </b>", eventWebsite);
        fillTextView(descriptionView, "", eventDescription);
    }

    private void fillTextView(TextView textView, String title, String text){
        if(text != null)
            textView.setText(Html.fromHtml(title + text));
        else
            textView.setVisibility(TextView.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
