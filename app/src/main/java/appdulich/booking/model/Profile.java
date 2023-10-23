package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile implements Serializable {
    private long id;
    @SerializedName("name")
    private String name;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("age")
    private int age;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("soCCCD")
    private String soCCCD;

    @SerializedName("diaChiNha")
    private String diaChiNha;

    @SerializedName("phuongXa")
    private String phuongXa;

    @SerializedName("huyenThi")
    private String huyenThi;

    @SerializedName("tinhThanh")
    private String tinhThanh;

    @SerializedName("user")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSoCCCD() {
        return soCCCD;
    }

    public void setSoCCCD(String soCCCD) {
        this.soCCCD = soCCCD;
    }

    public String getDiaChiNha() {
        return diaChiNha;
    }

    public void setDiaChiNha(String diaChiNha) {
        this.diaChiNha = diaChiNha;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public String getHuyenThi() {
        return huyenThi;
    }

    public void setHuyenThi(String huyenThi) {
        this.huyenThi = huyenThi;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile(
            String name,
            String lastName,
            int age,
            String phoneNumber,
            String soCCCD,
            String diaChiNha,
            String phuongXa,
            String huyenThi,
            String tinhThanh,
            User user) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.soCCCD = soCCCD;
        this.diaChiNha = diaChiNha;
        this.phuongXa = phuongXa;
        this.huyenThi = huyenThi;
        this.tinhThanh = tinhThanh;
        this.user = user;
    }

    public Profile() {
    }
}
