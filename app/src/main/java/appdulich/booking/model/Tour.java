package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tour implements Serializable {
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("giaThamKhao")
    private double giaThamKhao;
    @SerializedName("ngayGioXuatPhat")
    private String ngayGioXuatPhat;
    @SerializedName("ngayVe")
    private String ngayVe;
    @SerializedName("soLuongVe")
    private int soLuongVe;

    @SerializedName("tomTat")
    private String tomTat;

    @SerializedName("noiKhoiHanh")
    private String noiKhoiHanh;

    @SerializedName("imageUrls")
    private String imageUrls;

    @SerializedName("lichTrinhTours")
    private List<LichTrinhTour> lichTrinhTours = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGiaThamKhao() {
        return giaThamKhao;
    }

    public void setGiaThamKhao(double giaThamKhao) {
        this.giaThamKhao = giaThamKhao;
    }

    public String getNgayGioXuatPhat() {
        return ngayGioXuatPhat;
    }

    public void setNgayGioXuatPhat(String ngayGioXuatPhat) {
        this.ngayGioXuatPhat = ngayGioXuatPhat;
    }

    public String getNgayVe() {
        return ngayVe;
    }

    public void setNgayVe(String ngayVe) {
        this.ngayVe = ngayVe;
    }

    public int getSoLuongVe() {
        return soLuongVe;
    }

    public void setSoLuongVe(int soLuongVe) {
        this.soLuongVe = soLuongVe;
    }

    public String getTomTat() {
        return tomTat;
    }

    public void setTomTat(String tomTat) {
        this.tomTat = tomTat;
    }

    public String getNoiKhoiHanh() {
        return noiKhoiHanh;
    }

    public void setNoiKhoiHanh(String noiKhoiHanh) {
        this.noiKhoiHanh = noiKhoiHanh;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<LichTrinhTour> getLichTrinhTours() {
        return lichTrinhTours;
    }

    public void setLichTrinhTours(List<LichTrinhTour> lichTrinhTours) {
        this.lichTrinhTours = lichTrinhTours;
    }

    public Tour(String name, double giaThamKhao, String ngayGioXuatPhat, String ngayVe, int soLuongVe, String tomTat, String noiKhoiHanh, String imageUrls, List<LichTrinhTour> lichTrinhTours) {
        this.name = name;
        this.giaThamKhao = giaThamKhao;
        this.ngayGioXuatPhat = ngayGioXuatPhat;
        this.ngayVe = ngayVe;
        this.soLuongVe = soLuongVe;
        this.tomTat = tomTat;
        this.noiKhoiHanh = noiKhoiHanh;
        this.imageUrls = imageUrls;
        this.lichTrinhTours = lichTrinhTours;
    }

    public Tour(){}
}
