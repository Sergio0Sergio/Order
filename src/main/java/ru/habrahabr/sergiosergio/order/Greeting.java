package ru.habrahabr.sergiosergio.order;

import java.util.ArrayList;
import java.util.List;

public class Greeting {

    private String inn;
    private String captcha;
    private List<LogContentUnit> logContent = new ArrayList<>();
    private String theChosenCase;
    private String finalString;

    public String getFinalString() {
        return finalString;
    }

    public void setFinalString(String finalString) {
        this.finalString = finalString;
    }

    public String getTheChosenCase() {
        return theChosenCase;
    }

    public void setTheChosenCase(String theChosenCase) {
        this.theChosenCase = theChosenCase;
    }

    public List<LogContentUnit> getLogContent() {
        return logContent;
    }

    public void setLogContent(List<LogContentUnit> logContent) {
        this.logContent = logContent;
    }

    public String getInn() {return inn;  }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
