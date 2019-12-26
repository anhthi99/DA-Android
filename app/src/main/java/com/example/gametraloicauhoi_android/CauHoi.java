package com.example.gametraloicauhoi_android;

import java.util.ArrayList;

public class CauHoi {
    private int id;
    private String noiDung;
    private int linhVucID;
    private String phuongAnA;
    private String phuongAnB;
    private String phuongAnC;
    private String phuongAnD;
    private String dapAn;

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }

    public String getPhuongAnD() {
        return phuongAnD;
    }

    public void setPhuongAnD(String phuongAnD) {
        this.phuongAnD = phuongAnD;
    }

    public String getPhuongAnC() {
        return phuongAnC;
    }

    public void setPhuongAnC(String phuongAnC) {
        this.phuongAnC = phuongAnC;
    }

    public String getPhuongAnB() {
        return phuongAnB;
    }

    public void setPhuongAnB(String phuongAnB) {
        this.phuongAnB = phuongAnB;
    }

    public String getPhuongAnA() {
        return phuongAnA;
    }

    public void setPhuongAnA(String phuongAnA) {
        this.phuongAnA = phuongAnA;
    }

    public int getLinhVucID() {
        return linhVucID;
    }

    public void setLinhVucID(int linhVucID) {
        this.linhVucID = linhVucID;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public CauHoi(int _id, String cauHoi,int lv_id, String a, String b, String c, String d,
                  String dapAn){
        setId(_id);
        setNoiDung(cauHoi);
        setLinhVucID(lv_id);
        setDapAn(dapAn);
        setPhuongAnA(a);
        setPhuongAnB(b);
        setPhuongAnC(c);
        setPhuongAnD(d);
    }
}
