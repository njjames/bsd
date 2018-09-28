package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity;

import java.util.Objects;

/**
 * Created by Administrator on 2017-5-9.
 */

public class BSD_wxyy_cl_pop_entity {

    private  String   peij_cx;
    private int reco_no1;
    private String peij_no;
    private String peij_mc;
    private String peij_th;
    private String peij_dw;
    private String qxjp;
    private String peij_kc;

    private Double peij_sl;

    private Double peij_ydj;

    public Double getPeij_ydj() {
        return peij_ydj;
    }

    public void setPeij_ydj(Double peij_ydj) {
        this.peij_ydj = peij_ydj;
    }

    public Double getPeij_sl() {
        return peij_sl;
    }

    public void setPeij_sl(Double peij_sl) {
        this.peij_sl = peij_sl;
    }

    public String getPeij_cx() {
        return peij_cx;
    }

    public void setPeij_cx(String peij_cx) {
        this.peij_cx = peij_cx;
    }

    public int getReco_no1() {
        return reco_no1;
    }

    public void setReco_no1(int reco_no1) {
        this.reco_no1 = reco_no1;
    }

    public String getPeij_no() {
        return peij_no;
    }

    public void setPeij_no(String peij_no) {
        this.peij_no = peij_no;
    }

    public String getPeij_mc() {
        return peij_mc;
    }

    public void setPeij_mc(String peij_mc) {
        this.peij_mc = peij_mc;
    }

    public String getPeij_th() {
        return peij_th;
    }

    public void setPeij_th(String peij_th) {
        this.peij_th = peij_th;
    }

    public String getPeij_dw() {
        return peij_dw;
    }

    public void setPeij_dw(String peij_dw) {
        this.peij_dw = peij_dw;
    }

    public String  getQxjp() {
        return qxjp;
    }

    public void setQxjp(String qxjp) {
        this.qxjp = qxjp;
    }

    public String getPeij_kc() {
        return peij_kc;
    }

    public void setPeij_kc(String peij_kc) {
        this.peij_kc = peij_kc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BSD_wxyy_cl_pop_entity that = (BSD_wxyy_cl_pop_entity) o;
        return Objects.equals(peij_no, that.peij_no);
    }

    @Override
    public int hashCode() {

        return Objects.hash(peij_no);
    }
}
