package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

public class DModel_MyDenketa {
    public DModel_MyDenketa() {
    }

    String strName;

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
