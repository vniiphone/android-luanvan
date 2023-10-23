package appdulich.booking.model;

import java.util.Date;

public class LoaiTour {
    int id;
    String tentour;
    String mota;
    String hinhanh;
    Date ngaydi;
    Date ngayve;

    public String getTentour() {
        return tentour;
    }

    public void setTentour(String tentour) {
        this.tentour = tentour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Date getNgaydi() {
        return ngaydi;
    }

    public void setNgaydi(Date ngaydi) {
        this.ngaydi = ngaydi;
    }

    public Date getNgayve() {
        return ngayve;
    }

    public void setNgayve(Date ngayve) {
        this.ngayve = ngayve;
    }
}
