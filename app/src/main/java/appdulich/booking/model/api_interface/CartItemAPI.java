package appdulich.booking.model.api_interface;

import java.util.List;

import appdulich.booking.model.CartItem;
import appdulich.booking.model.GioHang;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartItemAPI {
    @POST("/api/Booking/create")
    Call<CartItem> newBooking(@Body CartItem cartItem);

    @GET("/api/Booking/getBooking/{id}")
    Call<List<GioHang>> getBooking(@Path("id") Long user_id);

    @GET("/api/Booking/getBookingByItemId/{id}")
    Call<GioHang> getBookingSingle(@Path("id") Long cartId);


    @POST("/api/Booking/create")
    Call<GioHang> createCartItem(
            @Query("user_id") Long userId,
            @Query("tour_id") Long tourId,
            @Query("soLuongVeDat") int quantity);

    @PUT("/api/Booking/{id}")
    Call<CartItem> updateItem(
            @Path("id") Long cart_id,
            @Query("user_id") Long userId,
            @Query("tour_id") Long tourId,
            @Query("soLuongVeDat") int quantity);

    @DELETE("/api/Booking/{id}")
    Call<Void> deleteBooking(@Path("id") long bookingId);



}
