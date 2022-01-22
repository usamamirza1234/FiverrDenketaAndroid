package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results;

public class DModelResults {
    public DModelResults() {
    }

    public DModelResults(String master, String investigator, String date, String time, String regiltor_used,int _ID,String investegorNumber,String masterNam) {
        Master = master;
        Investigator = investigator;
        Date = date;
        Time = time;
        Regiltor_used = regiltor_used;
        ID = _ID;
        investegorNumber = investegorNumber;
        masterName = masterName;
    }

    String Master;
    String Investigator;
    String Date;
    String Time;
    String Regiltor_used;
    int ID;
    private String investegorNumber;
    private String masterName;

    public String getInvestegorNumber() {
        return investegorNumber;
    }

    public void setInvestegorNumber(String investegorNumber) {
        this.investegorNumber = investegorNumber;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaster() {
        return Master;
    }

    public void setMaster(String master) {
        Master = master;
    }

    public String getInvestigator() {
        return Investigator;
    }

    public void setInvestigator(String investigator) {
        Investigator = investigator;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getRegiltor_used() {
        return Regiltor_used;
    }

    public void setRegiltor_used(String regiltor_used) {
        Regiltor_used = regiltor_used;
    }
}
