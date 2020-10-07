package Items;

import java.io.Serializable;

public class UserItem implements Serializable {

    private String signInModelId;
    private String username;
    private String password;
    private boolean canAddUser;

    public UserItem(String signInModelId, String username, String password, boolean canAddUserBool) {
        this.signInModelId = signInModelId;
        this.username = username;
        this.password = password;
        this.canAddUser = canAddUserBool;
    }

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

    public boolean isCanAddUserBool() {
        return canAddUser;
    }

    public void setCanAddUserBool(boolean canAddUserBool) {
        this.canAddUser = canAddUserBool;
    }
}
