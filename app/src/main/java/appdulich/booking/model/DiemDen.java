package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DiemDen implements Serializable {
    private Long id;
    @SerializedName("name")
    private String name;

    @SerializedName("diaChi")
    private String diaChi;
    @SerializedName("noiDung")
    private String noiDung;

    @SerializedName("giaVeThamQuan")
    private int giaVeThamQuan;

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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getGiaVeThamQuan() {
        return giaVeThamQuan;
    }

    public void setGiaVeThamQuan(int giaVeThamQuan) {
        this.giaVeThamQuan = giaVeThamQuan;
    }

    public DiemDen(String name, String diaChi, String noiDung, int giaVeThamQuan) {
        this.name = name;
        this.diaChi = diaChi;
        this.noiDung = noiDung;
        this.giaVeThamQuan = giaVeThamQuan;
    }
}
