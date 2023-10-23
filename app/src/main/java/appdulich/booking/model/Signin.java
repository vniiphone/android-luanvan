package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

public class Signin {
    @SerializedName("username")
    private  String username;
    @SerializedName("password")
    private  String password;


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

    public Signin(String username, String password ) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{"+
                "username"+":"+ "\""+username+"\"," +
                "\"password\":" + "\"" + password + "\"" +
                "}";
    }
}
