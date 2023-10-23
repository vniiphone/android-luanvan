package appdulich.booking.model.api_interface;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Publics {


//+ Khai báo biến chuỗi lưu địa chỉ Web Server gởi và nhận dữ liệu,
//+ Thêm phương thức kiểm tra trạng thái kết nối Internet,
//+ Thêm phương thức đổi từ InputStream (dữ liệu nhận từ Server) thành String.

    public static String URLNHANDULIEU =
            "https://10.0.2.2:8088/api/";
    public static String URLGOIDULIEULogIn =
            "http://10.0.2.2:8088/api/auth/signin";
    public static String URLGOIDULIEUSignup =
            "http://10.0.2.2:8088/api/auth/signup";
    public static String URLaddCategory =
            "http://10.0.2.2:8088/api/category/create";



    public static boolean HasInternet(Context context) {
        ConnectivityManager conn = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conn.getActiveNetworkInfo();
        return ((netInfo != null) && (netInfo.isConnected()));
    }

    public static String StreamToString(InputStream tream){
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(tream));
        String temp;
        try {
            while ((temp = reader.readLine()) != null) {
                builder.append(temp).append('\n');
            }
            tream.close();
        } catch (IOException e) {
        }
        return builder.toString();
    }


}
