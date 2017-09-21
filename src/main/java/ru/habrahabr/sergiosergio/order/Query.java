package ru.habrahabr.sergiosergio.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("captcha")
    @Expose
    private String captcha;
    @SerializedName("ogrninnfl")
    @Expose
    private Object ogrninnfl;
    @SerializedName("fam")
    @Expose
    private Object fam;
    @SerializedName("nam")
    @Expose
    private Object nam;
    @SerializedName("otch")
    @Expose
    private Object otch;
    @SerializedName("region")
    @Expose
    private Object region;
    @SerializedName("ogrninnul")
    @Expose
    private String ogrninnul;
    @SerializedName("namul")
    @Expose
    private Object namul;
    @SerializedName("regionul")
    @Expose
    private Object regionul;
    @SerializedName("ul")
    @Expose
    private Boolean ul;
    @SerializedName("searchByOgrn")
    @Expose
    private Boolean searchByOgrn;
    @SerializedName("nameEq")
    @Expose
    private Boolean nameEq;
    @SerializedName("searchByOgrnip")
    @Expose
    private Boolean searchByOgrnip;
    @SerializedName("kind")
    @Expose
    private String kind;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Object getOgrninnfl() {
        return ogrninnfl;
    }

    public void setOgrninnfl(Object ogrninnfl) {
        this.ogrninnfl = ogrninnfl;
    }

    public Object getFam() {
        return fam;
    }

    public void setFam(Object fam) {
        this.fam = fam;
    }

    public Object getNam() {
        return nam;
    }

    public void setNam(Object nam) {
        this.nam = nam;
    }

    public Object getOtch() {
        return otch;
    }

    public void setOtch(Object otch) {
        this.otch = otch;
    }

    public Object getRegion() {
        return region;
    }

    public void setRegion(Object region) {
        this.region = region;
    }

    public String getOgrninnul() {
        return ogrninnul;
    }

    public void setOgrninnul(String ogrninnul) {
        this.ogrninnul = ogrninnul;
    }

    public Object getNamul() {
        return namul;
    }

    public void setNamul(Object namul) {
        this.namul = namul;
    }

    public Object getRegionul() {
        return regionul;
    }

    public void setRegionul(Object regionul) {
        this.regionul = regionul;
    }

    public Boolean getUl() {
        return ul;
    }

    public void setUl(Boolean ul) {
        this.ul = ul;
    }

    public Boolean getSearchByOgrn() {
        return searchByOgrn;
    }

    public void setSearchByOgrn(Boolean searchByOgrn) {
        this.searchByOgrn = searchByOgrn;
    }

    public Boolean getNameEq() {
        return nameEq;
    }

    public void setNameEq(Boolean nameEq) {
        this.nameEq = nameEq;
    }

    public Boolean getSearchByOgrnip() {
        return searchByOgrnip;
    }

    public void setSearchByOgrnip(Boolean searchByOgrnip) {
        this.searchByOgrnip = searchByOgrnip;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
