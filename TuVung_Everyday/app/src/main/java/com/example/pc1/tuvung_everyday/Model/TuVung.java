package com.example.pc1.tuvung_everyday.Model;

/**
 * Created by pc1 on 7/5/2017.
 */

public class TuVung {
    private int ID;
    private String tiengAnh ;
    private String tiengViet;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String rating;
    public TuVung(){}
    public TuVung( String tiengAnh, String tiengViet, String ngayBatDau, String ngayKetThuc, String rating) {

        this.tiengAnh = tiengAnh;
        this.tiengViet = tiengViet;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.rating = rating;
    }
    public TuVung(int ID, String tiengAnh, String tiengViet, String ngayBatDau, String ngayKetThuc, String rating) {
        this.ID = ID;
        this.tiengAnh = tiengAnh;
        this.tiengViet = tiengViet;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.rating = rating;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTiengAnh() {
        return tiengAnh;
    }

    public void setTiengAnh(String tiengAnh) {
        this.tiengAnh = tiengAnh;
    }

    public String getTiengViet() {
        return tiengViet;
    }

    public void setTiengViet(String tiengViet) {
        this.tiengViet = tiengViet;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
