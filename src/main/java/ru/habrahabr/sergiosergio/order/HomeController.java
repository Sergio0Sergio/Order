package ru.habrahabr.sergiosergio.order;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
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
    public String captchaToken;
    private Greeting greeting;
    Document webPage;
    Connection conn;
    StringBuilder stringBuilder;

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
        System.out.println(captchaToken);

        //System.out.println(captchaToken);
        model.addAttribute("imageSource", imageSourse );
        return "greeting";
    }
    @PostMapping(value = "/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting){
        this.greeting = greeting;
        System.out.println(greeting.getCaptcha());
        System.out.println(greeting.getInn());
        System.out.println(captchaToken);

        try {
            conn = Jsoup.connect("https://egrul.nalog.ru/");
            conn.data("kind", "ul");
            conn.data("srchUl", "ogrn");
            conn.data("ogrninnul", greeting.getInn());
            conn.data("srchFl", "ogrn");
            conn.data("captcha", greeting.getCaptcha());
            conn.data("captchaToken", captchaToken);
            conn.method(Connection.Method.POST);
            //Connection.Response resp = conn.execute();
            webPage = conn.url("https://egrul.nalog.ru/").get();

        } catch (IOException e) {
            System.err.println("страница ФНС недоступна.");
            e.printStackTrace();
        }

        Element resultContent = webPage.getElementById("resultContent");
        Element table = webPage.getElementsByTag("tbody").first();
        Element row = table.select("tr").get(0);
        Elements cols = row.select("td");
        for (int i = 0; i < 6; i++){

            if (i != 4) {
                stringBuilder.append(cols.get(i).text() + " ");
            }
        }
        System.out.println(stringBuilder.toString());

        



        return "result";
    }
}
