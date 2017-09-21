package ru.habrahabr.sergiosergio.order;

import com.google.gson.Gson;
import com.sun.deploy.net.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private String imageSourse;
    public String captchaToken;
    private Greeting greeting;
    Document webPage;
    CloseableHttpResponse response2;
    Responce result;

    String json;

    Gson gson = new Gson();


    //Connection conn;
    //StringBuilder stringBuilder;
//    Connection.Response response;
//    URL obj;
//    HttpURLConnection conn;
//    DataOutputStream wr;
//    StringBuffer response;

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
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        this.greeting = greeting;
        System.out.println(greeting.getCaptcha());
        System.out.println(greeting.getInn());
        System.out.println(captchaToken);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://egrul.nalog.ru/");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("kind", "ul"));
        nvps.add(new BasicNameValuePair("srchUl", "ogrn"));
        nvps.add(new BasicNameValuePair("ogrninnul", greeting.getInn()));
        nvps.add(new BasicNameValuePair("srchFl", "ogrn"));
        nvps.add(new BasicNameValuePair("captcha", greeting.getCaptcha()));
        nvps.add(new BasicNameValuePair("captchaToken", captchaToken));
        try {


            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");


        } catch (UnsupportedEncodingException e) {
            System.err.println("не удалось добавить хедеры или энтити");
            e.printStackTrace();
        }
        try {
            response2 = httpClient.execute(httpPost);
            json = EntityUtils.toString(response2.getEntity());

        } catch (IOException e) {
            System.err.println("не удалось получить респонс");
            e.printStackTrace();
        }

        result = gson.fromJson(json, Responce.class);
        Row row = result.getRows().get(0);
        String finalString = row.getNAME()+ row.getADRESTEXT() + row.getOGRN() + row.getINN() + row.getDTREG();

        System.out.println(finalString);
        /*
        Начинается часть работы с документами Microsoft
         */


//        try {
//            json = EntityUtils.toString(response2.getEntity(), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return "result";
    }







}
