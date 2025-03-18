package deso1.tranminhhieu.dlu_22a1001d0125.Model;

import java.io.Serializable;

public class MonAn implements Serializable {
    private int id;
    private String tenMonAn;
    private float giaTien;
    private String donViTinh;
    private String hinhAnh;

    public MonAn(int id, String tenMonAn, float giaTien, String donViTinh, String hinhAnh) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.donViTinh = donViTinh;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setMaMonAN(int id) {
        this.id = id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public float getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(float giaTien) {
        this.giaTien = giaTien;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
