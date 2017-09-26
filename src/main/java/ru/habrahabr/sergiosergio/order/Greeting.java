package ru.habrahabr.sergiosergio.order;

import java.util.ArrayList;
import java.util.List;

public class Greeting {

    private String inn;
    private String captcha;
    private List<LogContent> logContent =new ArrayList<>();

    public List<LogContent> getLogContent() {
        return logContent;
    }

    public void setLogContent(List<LogContent> logContent) {
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
