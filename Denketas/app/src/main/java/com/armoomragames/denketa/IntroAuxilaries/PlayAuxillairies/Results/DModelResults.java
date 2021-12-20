package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results;

public class DModelResults {
    public DModelResults() {
    }

    public DModelResults(String master, String investigator, String date, String time, String regiltor_used) {
        Master = master;
        Investigator = investigator;
        Date = date;
        Time = time;
        Regiltor_used = regiltor_used;
    }

    String Master;
    String Investigator;
    String Date;
    String Time;
    String Regiltor_used;

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
