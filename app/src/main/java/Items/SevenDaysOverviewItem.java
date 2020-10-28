package Items;

public class SevenDaysOverviewItem {

    private String date;
    private int locationChanges;
    private int sanitations;

    public SevenDaysOverviewItem(int locationChanges, int sanitations, String date) {
        this.locationChanges = locationChanges;
        this.sanitations = sanitations;
        this.date = date;
    }

    public int getLocationChanges() {
        return locationChanges;
    }

    public void setLocationChanges(int locationChanges) {
        this.locationChanges = locationChanges;
    }

    public int getSanitations() {
        return sanitations;
    }

    public void setSanitations(int sanitations) {
        this.sanitations = sanitations;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
