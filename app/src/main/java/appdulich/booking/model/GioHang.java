package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GioHang implements Serializable {

    private long id;
    @SerializedName("soLuongVeDat")
    private int soLuongVeDat;

    @SerializedName("statusDat")
    private int statusDat;

    @SerializedName("user")
    private User user;

    @SerializedName("tour")
    private Tour tour;

    @SerializedName("hoaDon")
    private Invoice hoaDon;

    public GioHang(int soLuongVeDat, int statusDat, User user, Tour tour) {
        this.soLuongVeDat = soLuongVeDat;
        this.statusDat = statusDat;
        this.user = user;
        this.tour = tour;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSoLuongVeDat() {
        return soLuongVeDat;
    }

    public void setSoLuongVeDat(int soLuongVeDat) {
        this.soLuongVeDat = soLuongVeDat;
    }

    public int getStatusDat() {
        return statusDat;
    }

    public void setStatusDat(int statusDat) {
        this.statusDat = statusDat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
