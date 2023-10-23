package appdulich.booking.model.api_interface;

import java.util.List;

import appdulich.booking.model.CartItem;
import appdulich.booking.model.GioHang;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartAPI {
    @POST("api/cart/create")
    Call<CartItem> createCart4(@Body CartItem cartItem);

    @GET("/api/cart/getCart/{id}")
    Call<List<GioHang>> getCart(@Path("id") Long user_id);

    @FormUrlEncoded
    @POST("api/cart/create")
    Call<CartItem> createCart(@Field("user_id") long id, @Field("tour_id") long tourid, @Field("quantity") int quantity);

    @POST("api/cart/create")
    Call<CartItem> createCart2(@Body CartItem cartItem);

    @POST("api/cart/create")
    Call<CartItem> createCart3(@Query("tour_id") long tourid,
                               @Query("user_id") long userid,
                               @Query("quantity") int quantity);




    //http://localhost:8088/api/cart/getCart/1

}
