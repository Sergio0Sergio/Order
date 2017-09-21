package ru.habrahabr.sergiosergio.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Row {

    @SerializedName("T")
    @Expose
    private String t;
    @SerializedName("INN")
    @Expose
    private String iNN;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("OGRN")
    @Expose
    private String oGRN;
    @SerializedName("ADRESTEXT")
    @Expose
    private String aDRESTEXT;
    @SerializedName("CNT")
    @Expose
    private String cNT;
    @SerializedName("DTREG")
    @Expose
    private String dTREG;
    @SerializedName("KPP")
    @Expose
    private String kPP;

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getINN() {
        return iNN;
    }

    public void setINN(String iNN) {
        this.iNN = iNN;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getOGRN() {
        return oGRN;
    }

    public void setOGRN(String oGRN) {
        this.oGRN = oGRN;
    }

    public String getADRESTEXT() {
        return aDRESTEXT;
    }

    public void setADRESTEXT(String aDRESTEXT) {
        this.aDRESTEXT = aDRESTEXT;
    }

    public String getCNT() {
        return cNT;
    }

    public void setCNT(String cNT) {
        this.cNT = cNT;
    }

    public String getDTREG() {
        return dTREG;
    }

    public void setDTREG(String dTREG) {
        this.dTREG = dTREG;
    }

    public String getKPP() {
        return kPP;
    }

    public void setKPP(String kPP) {
        this.kPP = kPP;
    }
}
