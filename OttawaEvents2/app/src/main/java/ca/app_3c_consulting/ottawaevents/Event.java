package ca.app_3c_consulting.ottawaevents;

/**
 * Created by Oliver on 7/16/2015.
 */
public class Event {
    public static final int FAMILY_OUTING = 1;
    public static final int NATURE_EXPLORATION = 2;
    public static final int FRIENDLY_HANGOUTS = 3;
    public static final int SEASONAL_CELEBRATIONS = 4;
    public static final int SPORTS_PLAY = 5;
    public static final int DANCE_MUSIC_ARTS = 6;
    public static final int CULTURE = 7;
    public static final int NIGHTLIFE = 8;


    private String startDate;
    private String endDate;
    private String titleEnglish;
    private String titleFrench;
    private String summaryEnglish;
    private String summaryFrench;
    private String eventTimesEnglish;
    private String eventTimesFrench;
    private String locationEnglish;
    private String locationFrench;
    private String websiteURL;
    private String priceInfoEnglish;
    private String priceInfoFrench;
    private String id;

    private String databaseName;

    private int category;
    private boolean pinned = false;

    public Event(){}

    public Event(String titleEnglish, String startDate, String endDate, String locationEnglish){
        this.titleEnglish = titleEnglish;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationEnglish = locationEnglish;
    }

    public Event(String titleEnglish, String startDate, String endDate, String locationEnglish, String summaryEnglish){
        this(titleEnglish, startDate, endDate, locationEnglish);
        this.summaryEnglish = summaryEnglish;
    }

    public Event(String titleEnglish, String startDate, String endDate, String locationEnglish, String summaryEnglish, String eventTimesEnglish){
        this(titleEnglish, startDate, endDate, locationEnglish, summaryEnglish);
        this.eventTimesEnglish = eventTimesEnglish;
    }

    public Event(String titleEnglish, String startDate, String endDate, String locationEnglish, String summaryEnglish, String eventTimesEnglish, String websiteURL){
        this(titleEnglish, startDate, endDate, locationEnglish, summaryEnglish, eventTimesEnglish);
        this.websiteURL = websiteURL;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleFrench() {
        return titleFrench;
    }

    public void setTitleFrench(String titleFrench) {
        this.titleFrench = titleFrench;
    }

    public String getSummaryEnglish() {
        return summaryEnglish;
    }

    public void setSummaryEnglish(String summaryEnglish) {
        this.summaryEnglish = summaryEnglish;
    }

    public String getSummaryFrench() {
        return summaryFrench;
    }

    public void setSummaryFrench(String summaryFrench) {
        this.summaryFrench = summaryFrench;
    }

    public String getEventTimesEnglish() {
        return eventTimesEnglish;
    }

    public void setEventTimesEnglish(String eventTimesEnglish) {
        this.eventTimesEnglish = eventTimesEnglish;
    }

    public String getEventTimesFrench() {
        return eventTimesFrench;
    }

    public void setEventTimesFrench(String eventTimesFrench) {
        this.eventTimesFrench = eventTimesFrench;
    }

    public String getLocationEnglish() {
        return locationEnglish;
    }

    public void setLocationEnglish(String locationEnglish) {
        this.locationEnglish = locationEnglish;
    }

    public String getLocationFrench() {
        return locationFrench;
    }

    public void setLocationFrench(String locationFrench) {
        this.locationFrench = locationFrench;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getPriceInfoEnglish() {
        return priceInfoEnglish;
    }

    public void setPriceInfoEnglish(String priceInfoEnglish) {
        this.priceInfoEnglish = priceInfoEnglish;
    }

    public String getPriceInfoFrench() {
        return priceInfoFrench;
    }

    public void setPriceInfoFrench(String priceInfoFrench) {
        this.priceInfoFrench = priceInfoFrench;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isPinned(){return pinned;}

    public void setPinned(boolean isPinned){pinned = isPinned;}


    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
