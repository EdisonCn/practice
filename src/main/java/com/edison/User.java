package com.edison;

/**
 * Created by wangzhengfei on 16/7/1.
 */
public class User {

    private String userId;

    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
