package appdulich.booking.model.api_interface;

import java.util.List;

import appdulich.booking.model.Category;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPI {
    @GET("loai-tour")
    Call<List<Category>> getAllCategory();

//    @POST("/api/category/create")
//    Call<Category> createCategory(@Body Category category);
//

}
