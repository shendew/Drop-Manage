package com.kingdew.pos3;

public class PRODUCTS {
    private int prono;
    private String productnum,pro_title,pro_link;

    public PRODUCTS(){


    }

    public PRODUCTS(int prono, String productnum, String pro_title, String pro_link) {
        this.prono = prono;
        this.productnum = productnum;
        this.pro_title = pro_title;
        this.pro_link = pro_link;
    }

    public PRODUCTS(String productnum, String pro_title, String pro_link) {
        this.productnum = productnum;
        this.pro_title = pro_title;
        this.pro_link = pro_link;
    }

    public int getProno() {
        return prono;
    }

    public void setProno(int prono) {
        this.prono = prono;
    }

    public String getProductnum() {
        return productnum;
    }

    public void setProductnum(String productnum) {
        this.productnum = productnum;
    }

    public String getPro_title() {
        return pro_title;
    }

    public void setPro_title(String pro_title) {
        this.pro_title = pro_title;
    }

    public String getPro_link() {
        return pro_link;
    }

    public void setPro_link(String pro_link) {
        this.pro_link = pro_link;
    }
}
