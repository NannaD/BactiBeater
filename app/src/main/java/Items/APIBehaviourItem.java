package Items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class APIBehaviourItem {

    @SerializedName("behaviourModelId")
    @Expose
    private long behaviourModelId;
    @SerializedName("bactiBeaterId")
    @Expose
    private int bactiBeaterId;
    @SerializedName("beaconId")
    @Expose
    private int beaconId;
    @SerializedName("beaconInteractionTime")
    @Expose
    private Date beaconInteractionTime;
    @SerializedName("beaconName")
    @Expose
    private String beaconName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("didSanitize")
    @Expose
    private boolean didSanitize;

    public long getBehaviourModelId() {
        return behaviourModelId;
    }

    public void setBehaviourModelId(long behaviourModelId) {
        this.behaviourModelId = behaviourModelId;
    }

    public int getBactiBeaterId() {
        return bactiBeaterId;
    }

    public void setBactiBeaterId(int bactiBeaterId) {
        this.bactiBeaterId = bactiBeaterId;
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    public Date getBeaconInteractionTime() {
        return beaconInteractionTime;
    }

    public void setBeaconInteractionTime(Date beaconInteractionTime) {
        this.beaconInteractionTime = beaconInteractionTime;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String roomName) {
        this.beaconName = roomName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getDidSanitize() {
        return didSanitize;
    }

    public void setDidSanitize(boolean didSanitizeBool) {
        this.didSanitize = didSanitizeBool;
    }
}
