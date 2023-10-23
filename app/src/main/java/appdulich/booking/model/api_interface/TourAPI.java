package appdulich.booking.model.api_interface;

import java.util.List;

import appdulich.booking.model.Tour;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TourAPI {
    @GET("/api/tour/TourFilterVisible")
    Call<List<Tour>> getAllTour();

    @GET("/api/search/tour/")
    Call<List<Tour>> searchTour(@Query("keyword") String keyword);

    @GET("/api/tour/TourByLT/{id}")
    Call<List<Tour>> getToursByCate(@Query("id") Long category_id);

    @GET("/api/tour/{id}")
    Call<Tour> getTourDetailByID(@Path("id") Long id);

}
