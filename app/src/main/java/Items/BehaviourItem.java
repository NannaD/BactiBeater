package Items;

import java.io.Serializable;
import java.util.Date;

public class BehaviourItem implements Serializable {

    private long behaviourModelId;
    private int bactiBeaterId;
    private int beaconId;
    private Date beaconInteractionTime;
    private String beaconName;
    private boolean didSanitizeBool;

    public BehaviourItem(long behaviourModelId, int bactiBeaterId, int beaconId, Date beaconInteractionTime, String beaconName, boolean didSanitizeBool) {
        this.behaviourModelId = behaviourModelId;
        this.bactiBeaterId = bactiBeaterId;
        this.beaconId = beaconId;
        this.beaconInteractionTime = beaconInteractionTime;
        this.beaconName = beaconName;
        this.didSanitizeBool = didSanitizeBool;
    }

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

    public String getRoomName() {
        return beaconName;
    }

    public void setRoomName(String roomName) {
        this.beaconName = roomName;
    }

    public boolean isDidSanitizeBool() {
        return didSanitizeBool;
    }

    public void setDidSanitizeBool(boolean didSanitizeBool) {
        this.didSanitizeBool = didSanitizeBool;
    }
}
