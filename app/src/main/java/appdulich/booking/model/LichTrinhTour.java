package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LichTrinhTour implements Serializable {
    private Long id;

    @SerializedName("phuongTien")
    private String phuongTien;

    @SerializedName("sttLichTrinh")
    private int sttLichTrinh;

    @SerializedName("tenLichTrinh")
    private String tenLichTrinh;
    @SerializedName("ghiChu")
    private String ghiChu;



    @SerializedName("lichTrinhChiTiet")
    private String lichTrinhChiTiet;

    @SerializedName("diemDen")
    private List<DiemDen> diemDen = new ArrayList<>();

    @SerializedName("nameKhachSan")
    private String nameKhachSan;

    @SerializedName("phoneKhachSan")
    private String phoneKhachSan;
    @SerializedName("diaChiKhachSan")
    private String diaChiKhachSan;
    @SerializedName("giaPhongKhachSan")
    private Double giaPhongKhachSan;
    public String getLichTrinhChiTiet() {
        return lichTrinhChiTiet;
    }

    public void setLichTrinhChiTiet(String lichTrinhChiTiet) {
        this.lichTrinhChiTiet = lichTrinhChiTiet;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhuongTien() {
        return phuongTien;
    }

    public void setPhuongTien(String phuongTien) {
        this.phuongTien = phuongTien;
    }

    public int getSttLichTrinh() {
        return sttLichTrinh;
    }

    public void setSttLichTrinh(int sttLichTrinh) {
        this.sttLichTrinh = sttLichTrinh;
    }

    public String getTenLichTrinh() {
        return tenLichTrinh;
    }

    public void setTenLichTrinh(String tenLichTrinh) {
        this.tenLichTrinh = tenLichTrinh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<DiemDen> getDiemDen() {
        return diemDen;
    }

    public void setDiemDen(List<DiemDen> diemDen) {
        this.diemDen = diemDen;
    }

    public String getNameKhachSan() {
        return nameKhachSan;
    }

    public void setNameKhachSan(String nameKhachSan) {
        this.nameKhachSan = nameKhachSan;
    }

    public String getPhoneKhachSan() {
        return phoneKhachSan;
    }

    public void setPhoneKhachSan(String phoneKhachSan) {
        this.phoneKhachSan = phoneKhachSan;
    }

    public String getDiaChiKhachSan() {
        return diaChiKhachSan;
    }

    public void setDiaChiKhachSan(String diaChiKhachSan) {
        this.diaChiKhachSan = diaChiKhachSan;
    }

    public Double getGiaPhongKhachSan() {
        return giaPhongKhachSan;
    }

    public void setGiaPhongKhachSan(Double giaPhongKhachSan) {
        this.giaPhongKhachSan = giaPhongKhachSan;
    }

    public LichTrinhTour(String phuongTien, int sttLichTrinh, String tenLichTrinh, String ghiChu, String lichTrinhChiTiet, List<DiemDen> diemDen, String nameKhachSan, String phoneKhachSan, String diaChiKhachSan, Double giaPhongKhachSan) {
        this.phuongTien = phuongTien;
        this.sttLichTrinh = sttLichTrinh;
        this.tenLichTrinh = tenLichTrinh;
        this.ghiChu = ghiChu;
        this.lichTrinhChiTiet = lichTrinhChiTiet;
        this.diemDen = diemDen;
        this.nameKhachSan = nameKhachSan;
        this.phoneKhachSan = phoneKhachSan;
        this.diaChiKhachSan = diaChiKhachSan;
        this.giaPhongKhachSan = giaPhongKhachSan;
    }
}
