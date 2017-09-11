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
    private String captchaToken;
    private Greeting greeting;
    Document webPage;
    Connection conn;

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
            webPage = Jsoup.connect("https://egrul.nalog.ru/").get();
        } catch (IOException e) {
            System.err.println("страница ФНС недоступна.");
            e.printStackTrace();
        }

        Element table = webPage.select("table").get(0);
        Elements row = table.select("tr");


        return "result";
    }
}
