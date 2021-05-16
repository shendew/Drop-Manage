package com.kingdew.pos3;

public class ORDERS {
    private int no;

    public int getNo() {
        return no;
    }


    private String sorderID,sorderTitle,scontactDetails,strackingNumber,sshippedDate,scompletedDate;


    public ORDERS(){


    }


    public ORDERS(int no, String sorderID, String sorderTitle, String scontactDetails, String strackingNumber, String sshippedDate, String scompletedDate) {
        this.no = no;
        this.sorderID = sorderID;
        this.sorderTitle = sorderTitle;
        this.scontactDetails = scontactDetails;
        this.strackingNumber = strackingNumber;
        this.sshippedDate = sshippedDate;
        this.scompletedDate = scompletedDate;
    }

    public ORDERS(String sorderID, String sorderTitle, String scontactDetails, String strackingNumber, String sshippedDate, String scompletedDate) {
        this.sorderID = sorderID;
        this.sorderTitle = sorderTitle;
        this.scontactDetails = scontactDetails;
        this.strackingNumber = strackingNumber;
        this.sshippedDate = sshippedDate;
        this.scompletedDate = scompletedDate;
    }




    public void setNo(int no) {
        this.no = no;
    }

    public String getSorderID() {
        return sorderID;
    }

    public void setSorderID(String sorderID) {
        this.sorderID = sorderID;
    }

    public String getSorderTitle() {
        return sorderTitle;
    }

    public void setSorderTitle(String sorderTitle) {
        this.sorderTitle = sorderTitle;
    }

    public String getScontactDetails() {
        return scontactDetails;
    }

    public void setScontactDetails(String scontactDetails) {
        this.scontactDetails = scontactDetails;
    }

    public String getStrackingNumber() {
        return strackingNumber;
    }

    public void setStrackingNumber(String strackingNumber) {
        this.strackingNumber = strackingNumber;
    }

    public String getSshippedDate() {
        return sshippedDate;
    }

    public void setSshippedDate(String sshippedDate) {
        this.sshippedDate = sshippedDate;
    }

    public String getScompletedDate() {
        return scompletedDate;
    }

    public void setScompletedDate(String scompletedDate) {
        this.scompletedDate = scompletedDate;
    }

}
