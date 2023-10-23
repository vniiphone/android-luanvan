package appdulich.booking.retrofit;

import android.content.Context;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    static final String TAG = "retrofit";
    private static Retrofit retrofit;
    private  Context context;
    private static TokenInterceptor tokenInterceptor;

    private static final String BASE_URL_IP = "http://10.0.2.2:8088/api/";


    public RetrofitService(Context context) {
        this.tokenInterceptor = new TokenInterceptor(); // Khởi tạo TokenInterceptor ở đây
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_IP)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }



 /*   public static void initialize(Context context) {
        tokenInterceptor = new TokenInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_IP)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }*/

    public void updateToken(String token) {
        tokenInterceptor.setToken(token); // Cập nhật token trong TokenInterceptor
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


   /* private static Retrofit retrofit;
    private static SessionManager sessionManager;

    //    private static final String BASE_URL = "http://10.0.2.2:8088/api";
    private static final String BASE_URL_IP = "http://10.0.2.2:8088/api/";

    public static void initialize(Context context) {
        sessionManager = new SessionManager(context);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(sessionManager.getToken()))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_IP)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }*/


/*
    public RetrofitService() {
        intitializeRetrofit();
    }
*/

/*    private void intitializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_IP) // Using your service URL if it working
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }*/


}
