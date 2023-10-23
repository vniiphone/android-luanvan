package appdulich.booking.payload;

import org.jetbrains.annotations.NotNull;

public class InvoiceRequest {
    @NotNull
    private Long user_id;

    @NotNull
    private Long booking_id;

    @NotNull
    private Long profile_id;

    @NotNull
    private Long tour_id;

    @NotNull
    public Long getUser_id() {
        return user_id;
    }

    public InvoiceRequest(
            @NotNull Long user_id,
            @NotNull Long booking_id,
            @NotNull Long profile_id,
            @NotNull Long tour_id) {
        this.user_id = user_id;
        this.booking_id = booking_id;
        this.profile_id = profile_id;
        this.tour_id = tour_id;
    }

    public void setUser_id(@NotNull Long user_id) {
        this.user_id = user_id;
    }

    @NotNull
    public Long getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(@NotNull Long booking_id) {
        this.booking_id = booking_id;
    }

    @NotNull
    public Long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(@NotNull Long profile_id) {
        this.profile_id = profile_id;
    }

    @NotNull
    public Long getTour_id() {
        return tour_id;
    }

    public void setTour_id(@NotNull Long tour_id) {
        this.tour_id = tour_id;
    }
}
