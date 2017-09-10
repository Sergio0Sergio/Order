package ru.habrahabr.sergiosergio.order;

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
    String imageSourse;
    private Greeting greeting;
    Document webPage;

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
