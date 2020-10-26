package Items;

public class SanitizeItem {

    private String location;
    private String date;
    private int visitorCount;
    private int sanitizeCount;

    public SanitizeItem(String location, String date, int visitorCount, int sanitizeCount) {
        this.location = location;
        this.date = date;
        this.visitorCount = visitorCount;
        this.sanitizeCount = sanitizeCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public int getSanitizeCount() {
        return sanitizeCount;
    }

    public void setSanitizeCount(int sanitizeCount) {
        this.sanitizeCount = sanitizeCount;
    }
}
