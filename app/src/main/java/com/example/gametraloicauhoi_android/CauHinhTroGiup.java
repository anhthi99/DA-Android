package com.example.gametraloicauhoi_android;

public class CauHinhTroGiup {
    private int id;
    private int loaiTroGiup;
    private int thuTu;
    private int credit;

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getThuTu() {
        return thuTu;
    }

    public void setThuTu(int thuTu) {
        this.thuTu = thuTu;
    }

    public int getLoaiTroGiup() {
        return loaiTroGiup;
    }

    public void setLoaiTroGiup(int loaiTroGiup) {
        this.loaiTroGiup = loaiTroGiup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CauHinhTroGiup(int id, int loai, int thutu, int credit){
        setId(id);
        setLoaiTroGiup(loai);
        setThuTu(thutu);
        setCredit(credit);
    }
}
