package appdulich.booking.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    public static ArrayList fromTimestamp(String value){
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    public static String arraylistToString(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        return json;
    }
}
