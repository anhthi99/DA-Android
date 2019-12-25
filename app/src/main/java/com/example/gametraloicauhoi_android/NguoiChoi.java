package com.example.gametraloicauhoi_android;

public class NguoiChoi {
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String email;
    private String hinhDaiDien;
    private int diemCaoNhat;
    private int credit;
    public static String token = null;

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDiemCaoNhat() {
        return diemCaoNhat;
    }

    public void setDiemCaoNhat(int diemCaoNhat) {
        this.diemCaoNhat = diemCaoNhat;
    }

    public String getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(String hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public NguoiChoi(int id, String tenDangNhap, int diem, int credit ){
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.diemCaoNhat = diem;
        this.credit = credit;
    }
}
