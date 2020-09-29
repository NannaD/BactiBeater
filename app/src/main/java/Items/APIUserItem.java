package Items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIUserItem {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("canAddUserBool")
    @Expose
    private boolean canAddUserBool;

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

    public boolean isCanAddUserBool() {
        return canAddUserBool;
    }

    public void setCanAddUserBool(boolean canAddUserBool) {
        this.canAddUserBool = canAddUserBool;
    }
}
