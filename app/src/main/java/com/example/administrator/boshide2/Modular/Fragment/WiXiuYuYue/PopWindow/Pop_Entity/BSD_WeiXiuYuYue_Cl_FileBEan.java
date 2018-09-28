package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity;

import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeId;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeLabel;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodePid;

/**
 * Created by Administrator on 2017-5-8.
 */

public class BSD_WeiXiuYuYue_Cl_FileBEan {

    @TreeNodeId(type = String.class)
    private String peijlb_dm;
    @TreeNodePid(type = String.class)
    private String peijlb_top;
    @TreeNodeLabel
    private String peijlb_mc;

    private String peijlb_qz;

    private int peijlb_len;

    public String getPeijlb_dm() {
        return peijlb_dm;
    }

    public void setPeijlb_dm(String peijlb_dm) {
        this.peijlb_dm = peijlb_dm;
    }

    public String getPeijlb_top() {
        return peijlb_top;
    }

    public void setPeijlb_top(String peijlb_top) {
        this.peijlb_top = peijlb_top;
    }

    public String getPeijlb_mc() {
        return peijlb_mc;
    }

    public void setPeijlb_mc(String peijlb_mc) {
        this.peijlb_mc = peijlb_mc;
    }

    public String getPeijlb_qz() {
        return peijlb_qz;
    }

    public void setPeijlb_qz(String peijlb_qz) {
        this.peijlb_qz = peijlb_qz;
    }

    public int getPeijlb_len() {
        return peijlb_len;
    }

    public void setPeijlb_len(int peijlb_len) {
        this.peijlb_len = peijlb_len;
    }

}
