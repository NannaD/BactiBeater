package Items;

import java.io.Serializable;
import java.util.Date;

public class BehaviourItem implements Serializable {

    private String behaviourModelId;
    private String bactiBeaterId;
    private String beaconId;
    private String beaconInteractionTime;
    private String beaconName;
    private boolean didSanitize;

    public BehaviourItem(String behaviourModelId, String bactiBeaterId, String beaconId, String beaconInteractionTime, String beaconName, boolean didSanitize) {
        this.behaviourModelId = behaviourModelId;
        this.bactiBeaterId = bactiBeaterId;
        this.beaconId = beaconId;
        this.beaconInteractionTime = beaconInteractionTime;
        this.beaconName = beaconName;
        this.didSanitize = didSanitize;
    }

    public String getBehaviourModelId() {
        return behaviourModelId;
    }

    public void setBehaviourModelId(String behaviourModelId) {
        this.behaviourModelId = behaviourModelId;
    }

    public String getBactiBeaterId() {
        return bactiBeaterId;
    }

    public void setBactiBeaterId(String bactiBeaterId) {
        this.bactiBeaterId = bactiBeaterId;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getBeaconInteractionTime() {
        return beaconInteractionTime;
    }

    public void setBeaconInteractionTime(String beaconInteractionTime) {
        this.beaconInteractionTime = beaconInteractionTime;
    }

    public String getRoomName() {
        return beaconName;
    }

    public void setRoomName(String roomName) {
        this.beaconName = roomName;
    }

    public boolean isDidSanitize() {
        return didSanitize;
    }

    public void setDidSanitize(boolean didSanitize) {
        this.didSanitize = didSanitize;
    }
}
