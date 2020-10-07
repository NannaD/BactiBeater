package Items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIUserItem {

    @SerializedName("signInModelId")
    @Expose
    private String signInModelId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("canAddUser")
    @Expose
    private boolean canAddUser;

    public String getSignInModelId() {
        return signInModelId;
    }

    public void setSignInModelId(String signInModelId) {
        this.signInModelId = signInModelId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getCanAddUser() {
        return canAddUser;
    }

    public void setCanAddUser(boolean canAddUser) {
        this.canAddUser = canAddUser;
    }
}
