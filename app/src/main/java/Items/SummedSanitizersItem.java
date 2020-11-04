package Items;

public class SummedSanitizersItem {
    private int sanitationLocationChangesTrue;
    private int getSanitationLocationChangesFalse;
    private String date;

    public SummedSanitizersItem(int sanitationLocationChangesTrue, int getSanitationLocationChangesFalse, String date) {
        this.sanitationLocationChangesTrue = sanitationLocationChangesTrue;
        this.getSanitationLocationChangesFalse = getSanitationLocationChangesFalse;
        this.date = date;
    }

    public int getSanitationLocationChangesTrue() {
        return sanitationLocationChangesTrue;
    }

    public void setSanitationLocationChangesTrue(int sanitationLocationChangesTrue) {
        this.sanitationLocationChangesTrue = sanitationLocationChangesTrue;
    }

    public int getGetSanitationLocationChangesFalse() {
        return getSanitationLocationChangesFalse;
    }

    public void setGetSanitationLocationChangesFalse(int getSanitationLocationChangesFalse) {
        this.getSanitationLocationChangesFalse = getSanitationLocationChangesFalse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
