package appdulich.booking.model.api_interface;

import java.util.List;

import appdulich.booking.model.LichTrinhTour;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LichTrinhTourAPI {


    @GET("/api/lichtrinh-tour/lichtrinhTour-chitiet/{id}")
    Call<List<LichTrinhTour>> getLichTrinhTourByTourID(@Path("id") Long id);


}
