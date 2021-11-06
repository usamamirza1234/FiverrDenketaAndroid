package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

public class DModel_MyDenketa {
    public DModel_MyDenketa() {
    }

    String strName;

    public DModel_MyDenketa(String strName, String strId, String strImage) {
        this.strName = strName;
        StrId = strId;
        this.strImage = strImage;
    }

    public String getStrId() {
        return StrId;
    }

    public void setStrId(String strId) {
        StrId = strId;
    }

    String StrId;

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrImage() {
        return strImage;
    }

    public void setStrImage(String strImage) {
        this.strImage = strImage;
    }

    public DModel_MyDenketa(String strName, String strImage) {
        this.strName = strName;
        this.strImage = strImage;
    }

    String strImage;
}
