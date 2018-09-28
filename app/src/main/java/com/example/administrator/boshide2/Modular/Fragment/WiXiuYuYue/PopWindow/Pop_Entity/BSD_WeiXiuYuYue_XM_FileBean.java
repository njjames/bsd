package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity;


import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeId;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeLabel;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodePid;

public class BSD_WeiXiuYuYue_XM_FileBean {

    @TreeNodeId(type = String.class)
    private String wxxm_lbdm;
    @TreeNodePid(type = String.class)
    private String wxxm_lbtop;
    @TreeNodeLabel
    private String wxxm_lbmc;
    private String wxxm_lbqz;
    private int wxxm_lblen;


    public String getWxxm_lbdm() {
        return wxxm_lbdm;
    }

    public void setWxxm_lbdm(String wxxm_lbdm) {
        this.wxxm_lbdm = wxxm_lbdm;
    }

    public String getWxxm_lbtop() {
        return wxxm_lbtop;
    }

    public void setWxxm_lbtop(String wxxm_lbtop) {
        this.wxxm_lbtop = wxxm_lbtop;
    }

    public String getWxxm_lbmc() {
        return wxxm_lbmc;
    }

    public void setWxxm_lbmc(String wxxm_lbmc) {
        this.wxxm_lbmc = wxxm_lbmc;
    }

    public String getWxxm_lbqz() {
        return wxxm_lbqz;
    }

    public void setWxxm_lbqz(String wxxm_lbqz) {
        this.wxxm_lbqz = wxxm_lbqz;
    }

    public int getWxxm_lblen() {
        return wxxm_lblen;
    }

    public void setWxxm_lblen(int wxxm_lblen) {
        this.wxxm_lblen = wxxm_lblen;
    }


}
