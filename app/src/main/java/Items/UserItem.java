package Items;

import java.io.Serializable;

public class UserItem implements Serializable {

    private String nono;
    private String username;
    private String password;
    private boolean canAddUserBool;

    public UserItem(String username, String password, boolean canAddUserBool) {
        this.username = username;
        this.password = password;
        this.canAddUserBool = canAddUserBool;
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
        return canAddUserBool;
    }

    public void setCanAddUserBool(boolean canAddUserBool) {
        this.canAddUserBool = canAddUserBool;
    }
}
