package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Invoice implements Serializable {
    private Long id;
    @SerializedName("user")
    private User user;
    @SerializedName("timeCreate")
    private String timeCreate;
    @SerializedName("paymentMethod")
    private String paymentMethod;
    @SerializedName("totalPrice")
    private double totalPrice;
    @SerializedName("wasPay")
    private boolean wasPay;
    @SerializedName("bookings")
    private List<CartItem> bookings;
    @SerializedName("tour")
    private Tour tour;
    @SerializedName("soLuongVeDaDat")
    private long soLuongVeDaDat;
    @SerializedName("profile")
    private Profile profile;

    public Invoice(
            User user,
            String timeCreate,
            String paymentMethod,
            double totalPrice,
            boolean wasPay,
            List<CartItem> bookings,
            Tour tour,
            long soLuongVeDaDat,
            Profile profile) {
        this.user = user;
        this.timeCreate = timeCreate;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.wasPay = wasPay;
        this.bookings = bookings;
        this.tour = tour;
        this.soLuongVeDaDat = soLuongVeDaDat;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isWasPay() {
        return wasPay;
    }

    public void setWasPay(boolean wasPay) {
        this.wasPay = wasPay;
    }

    public List<CartItem> getBookings() {
        return bookings;
    }

    public void setBookings(List<CartItem> bookings) {
        this.bookings = bookings;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public long getSoLuongVeDaDat() {
        return soLuongVeDaDat;
    }

    public void setSoLuongVeDaDat(long soLuongVeDaDat) {
        this.soLuongVeDaDat = soLuongVeDaDat;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
