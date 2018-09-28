package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity;

/**
 * Created by Administrator on 2017-11-13.
 */

public class BSD_WXCL_CK_Entity {

  private   String   ckMc;
  private   String   currentKc;  //当前库存
  private   String   pjDj;  //平均单价
  private   String   je;
  private   String   kcSum;  //库存上限
  private   String   kcMin;   //库存下限
  private   String   kw;     //库位

    public String getCkMc() {
        return ckMc;
    }

    public void setCkMc(String ckMc) {
        this.ckMc = ckMc;
    }

    public String getCurrentKc() {
        return currentKc;
    }

    public void setCurrentKc(String currentKc) {
        this.currentKc = currentKc;
    }

    public String getPjDj() {
        return pjDj;
    }

    public void setPjDj(String pjDj) {
        this.pjDj = pjDj;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getKcSum() {
        return kcSum;
    }

    public void setKcSum(String kcSum) {
        this.kcSum = kcSum;
    }

    public String getKcMin() {
        return kcMin;
    }

    public void setKcMin(String kcMin) {
        this.kcMin = kcMin;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }
}
