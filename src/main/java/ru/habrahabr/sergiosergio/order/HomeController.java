package ru.habrahabr.sergiosergio.order;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class HomeController {
    private String imageSourse;
    private String captchaToken;
    private Greeting greeting;
    Document webPage;
    Connection conn;
    StringBuilder strbuilder;

    @GetMapping("/greeting")
    public String greetingForm(Model model){
        model.addAttribute("greeting", new Greeting());

        try {
            webPage = Jsoup.connect("https://egrul.nalog.ru/").get();

        } catch (IOException e) {
            System.err.println("страница ФНС недоступна.");
            e.printStackTrace();
        }
        Element image = webPage.select("img").get(0);
        imageSourse = image.absUrl("src");
        Elements captchaField = webPage.getElementsByClass("form-field captcha-field");
        Elements inputElements = captchaField.select("input");
        Element captchaElement = inputElements.get(1);
        captchaToken = captchaElement.attr("value");

        //System.out.println(captchaToken);
        model.addAttribute("imageSource", imageSourse );
        return "greeting";
    }
    @PostMapping(value = "/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting){
        this.greeting = greeting;

        try {
            conn = Jsoup.connect("https://egrul.nalog.ru/");
            conn.data("kind", "ul");
            conn.data("srchUl", "ogrn");
            conn.data("ogrninnul", greeting.getInn());
            conn.data("srchFl", "ogrn");
            conn.data("captcha", greeting.getCaptcha());
            conn.data("captchaToken", captchaToken);
            conn.method(Connection.Method.POST);
            Connection.Response resp = conn.execute();
            webPage = conn.url("https://egrul.nalog.ru/").get();

        } catch (IOException e) {
            System.err.println("страница ФНС недоступна.");
            e.printStackTrace();
        }


        Element table = webPage.select("table").get(0);
        Element row = table.select("tr").first();
        Elements cols = row.select("td");
        


        return "result";
    }
}
