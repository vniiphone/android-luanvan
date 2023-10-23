package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

public class Signup {
    @SerializedName("username")
    private  String username;
    @SerializedName("password")
    private  String password;
    @SerializedName("email")
    private  String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{"+
                "username"+":"+ "\""+username+"\"," +
                "\"email\":" + "\"" + email + "\"," +
                "\"password\":" + "\"" + password + "\"" +
                "}";
    }
}
