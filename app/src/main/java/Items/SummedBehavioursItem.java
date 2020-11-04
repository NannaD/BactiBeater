package Items;

public class SummedBehavioursItem {
    private int locationChangesDidSanitize;
    private int locationChangesDidNotSanitize;
    private String date;

    public SummedBehavioursItem(int locationChangesDidSanitize, int locationChangesDidNotSanitize, String date) {
        this.locationChangesDidSanitize = locationChangesDidSanitize;
        this.locationChangesDidNotSanitize = locationChangesDidNotSanitize;
        this.date = date;
    }

    public int getLocationChangesDidSanitize() {
        return locationChangesDidSanitize;
    }

    public void setLocationChangesDidSanitize(int locationChangesDidSanitize) {
        this.locationChangesDidSanitize = locationChangesDidSanitize;
    }

    public int getLocationChangesDidNotSanitize() {
        return locationChangesDidNotSanitize;
    }

    public void setLocationChangesDidNotSanitize(int locationChangesDidNotSanitize) {
        this.locationChangesDidNotSanitize = locationChangesDidNotSanitize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
