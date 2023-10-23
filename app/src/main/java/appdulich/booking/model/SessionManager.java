package appdulich.booking.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "MyAppPref";
    private static final String KEY_TOKEN = "token";
    private static final long KEY_USER_ID = -1;
    private static final String KEY_EMAIL = "email";
    private  static final String KEY_USERNAME = "username";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public void dangXuat() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear(); // Xóa tất cả dữ liệu người dùng
        editor.apply();
    }

    public SharedPreferences.Editor clearToken() {
        editor.remove(KEY_TOKEN);
        editor.commit();
        return editor;
    }

    public void saveUserId(long id) {
        editor.putLong(String.valueOf(KEY_USER_ID), id);
        editor.commit();
    }

    public long getUserId() {
        return pref.getLong(String.valueOf(KEY_USER_ID), -1);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void saveEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getUsername(){
        return pref.getString(KEY_USERNAME, "");
    }
    public void saveUserName(String userName) {
        editor.putString(KEY_USERNAME, userName);
        editor.commit();
    }

}
