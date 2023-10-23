package appdulich.booking.datalocal;

import android.content.Context;

import com.google.gson.Gson;

import appdulich.booking.model.User;

public class DatalocalManager {
    private static final long PREF_ID_USER = 0;
    private static final String PREF_NAME_USER = "PREF_NAME_USER";
    private static final String PREF_EMAIL_USER = "PREF_EMAIL_USER";
    private static final String PREF_OBJECT_USER = "PREF_OBJECT_USER";

    private static DatalocalManager instance;
    public MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DatalocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }
    public static DatalocalManager getInstance() {
        if (instance == null) {
            instance = new DatalocalManager();
        }
        return instance;
    }

    //Convert qua json để lưu user với dạng string
    public static void setUser(User user) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DatalocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_USER, strJsonUser);

    }

    public static User getUser() {
        String strJsonUser = DatalocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(strJsonUser, User.class);
        return user;
    }
}
