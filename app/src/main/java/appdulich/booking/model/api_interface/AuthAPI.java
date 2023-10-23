package appdulich.booking.model.api_interface;

import appdulich.booking.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/auth/signup")
    Call<User> signUp(@Body User user);

    @POST("/api/auth/signin")
    Call<User> signIn(@Body User user);

/*
    Interceptor interceptor = chain -> {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Authorization","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdW9jdnUxMjMiLCJpYXQiOjE2ODE5MjEzNDksImV4cCI6MTY4MjAwNzc0OX0.FELSqpdQSuORqv4TT4Sk-O8DEFfTTLNKm679MYa4OyK2joO2Ja6GR_lCZZNiS2uUfRgirJeVGl-_P2i7Fyluhw");
        return chain.proceed(builder.build());
    };
*/

 /*   HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor);

*/

}
