package appdulich.booking.model.api_interface;

import java.util.List;

import appdulich.booking.model.Profile;
import appdulich.booking.payload.ProfileRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileUserAPI {

    @GET("/api/profile/user/{id}")
    Call<List<Profile>> getAllProfiles(@Path("id") Long user_id);

    @POST("/api/profile/create")
    Call<Profile> createProfile(@Body ProfileRequest profileRequest);

    @PUT("/api/profile/user/{profileId}")
    Call<Profile> updateProfile(@Path("profileId")Long profile_id,@Body ProfileRequest profileRequest);
}
