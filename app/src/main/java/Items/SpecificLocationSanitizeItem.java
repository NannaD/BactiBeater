package Items;

public class SpecificLocationSanitizeItem {

    private String location;
    private String date;
    private String procentage;
    private String visitorCount;
    private String sanitizeCount;

    public SpecificLocationSanitizeItem(String location, String date, String procentage, String visitorCount, String sanitizeCount) {
        this.location = location;
        this.date = date;
        this.procentage = procentage;
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

    public String getProcentage() {
        return procentage;
    }

    public void setProcentage(String procentage) {
        this.procentage = procentage;
    }

    public String getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(String visitorCount) {
        this.visitorCount = visitorCount;
    }

    public String getSanitizeCount() {
        return sanitizeCount;
    }

    public void setSanitizeCount(String sanitizeCount) {
        this.sanitizeCount = sanitizeCount;
    }
}
