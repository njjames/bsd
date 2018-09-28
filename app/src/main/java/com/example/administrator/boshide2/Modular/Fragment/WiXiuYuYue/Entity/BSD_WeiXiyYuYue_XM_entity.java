package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Table;

/**
 * Created by Administrator on 2017-5-4.
 */
@Table(name = "BSD_WeiXiyYuYue_XM_entity")
public class BSD_WeiXiyYuYue_XM_entity {
    public String getWxyy_xm_id() {
        return wxyy_xm_id;
    }

    public void setWxyy_xm_id(String wxyy_xm_id) {
        this.wxyy_xm_id = wxyy_xm_id;
    }

    @Column(name = "wxyy_xm_id")
    private  String wxyy_xm_id;
    @Column(name = "reco_no")
    private double reco_no;

    @Column(name = "yuyue_no")
    private String yuyue_no;

    @Column(name = "wxxm_no")
    private String wxxm_no;

    @Column(name = "wxxm_mc")
    private String wxxm_mc;

    @Column(name = "wxxm_gs")
    private double wxxm_gs;

    @Column(name = "wxxm_khgs")
    private double wxxm_khgs;

    @Column(name = "wxxm_cb")
    private double wxxm_cb;

    @Column(name = "wxxm_je")
    private double wxxm_je;

    @Column(name = "wxxm_yje")
    private double wxxm_yje;

    @Column(name = "wxxm_mxcx")
    private String wxxm_mxcx;

    @Column(name = "wxxm_zt")
    private String wxxm_zt;

    @Column(name = "wxxm_bz")
    private String wxxm_bz;

    @Column(name = "wxxm_dj")
    private double wxxm_dj;


    public double getReco_no() {
        return reco_no;
    }

    public void setReco_no(double reco_no) {
        this.reco_no = reco_no;
    }

    public String getYuyue_no() {
        return yuyue_no;
    }

    public void setYuyue_no(String yuyue_no) {
        this.yuyue_no = yuyue_no;
    }

    public String getWxxm_no() {
        return wxxm_no;
    }

    public void setWxxm_no(String wxxm_no) {
        this.wxxm_no = wxxm_no;
    }

    public String getWxxm_mc() {
        return wxxm_mc;
    }

    public void setWxxm_mc(String wxxm_mc) {
        this.wxxm_mc = wxxm_mc;
    }

    public double getWxxm_gs() {
        return wxxm_gs;
    }

    public void setWxxm_gs(double wxxm_gs) {
        this.wxxm_gs = wxxm_gs;
    }

    public double getWxxm_khgs() {
        return wxxm_khgs;
    }

    public void setWxxm_khgs(double wxxm_khgs) {
        this.wxxm_khgs = wxxm_khgs;
    }

    public double getWxxm_cb() {
        return wxxm_cb;
    }

    public void setWxxm_cb(double wxxm_cb) {
        this.wxxm_cb = wxxm_cb;
    }

    public double getWxxm_je() {
        return wxxm_je;
    }

    public void setWxxm_je(double wxxm_je) {
        this.wxxm_je = wxxm_je;
    }

    public double getWxxm_yje() {
        return wxxm_yje;
    }

    public void setWxxm_yje(double wxxm_yje) {
        this.wxxm_yje = wxxm_yje;
    }

    public String getWxxm_mxcx() {
        return wxxm_mxcx;
    }

    public void setWxxm_mxcx(String wxxm_mxcx) {
        this.wxxm_mxcx = wxxm_mxcx;
    }

    public String getWxxm_zt() {
        return wxxm_zt;
    }

    public void setWxxm_zt(String wxxm_zt) {
        this.wxxm_zt = wxxm_zt;
    }

    public String getWxxm_bz() {
        return wxxm_bz;
    }

    public void setWxxm_bz(String wxxm_bz) {
        this.wxxm_bz = wxxm_bz;
    }

    public double getWxxm_dj() {
        return wxxm_dj;
    }

    public void setWxxm_dj(double wxxm_dj) {
        this.wxxm_dj = wxxm_dj;
    }
}
