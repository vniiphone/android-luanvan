package appdulich.booking.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String DEVICE_URL = "http://192.168.0.1:8080/";
    private static final String BASE_URL = "http://10.0.2.2:8088/";
   // private static RetrofitAPI retrofitAPI;

/*
    public static RetrofitAPI getInstance() {
        if (retrofitAPI == null) {
            retrofitAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitAPI.class);
        }
        return retrofitAPI;
    }
*/

    public static Retrofit getClient(String url) {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    //10.0.2.2
  /*  private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;
*/
}
