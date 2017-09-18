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
    //BufferedReader reader;
    String json;
    Response gsonResult;


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
    public String greetingSubmit(@ModelAttribute Greeting greeting){
        this.greeting = greeting;
        System.out.println(greeting.getCaptcha());
        System.out.println(greeting.getInn());
        System.out.println(captchaToken);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://egrul.nalog.ru/");
        List <NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("kind", "ul"));
        nvps.add(new BasicNameValuePair("srchUl","ogrn"));
        nvps.add(new BasicNameValuePair("ogrninnul",greeting.getInn()));
        nvps.add(new BasicNameValuePair("srchFl","ogrn"));
        nvps.add(new BasicNameValuePair("captcha",greeting.getCaptcha()));
        nvps.add(new BasicNameValuePair("captchaToken",captchaToken));
        try {
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response2 = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            json = EntityUtils.toString(response2.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response2.getEntity();
        Gson gson = new Gson();
        try {
            gsonResult = gson.fromJson(EntityUtils.toString(entity), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");

        //HttpEntity entity2 = response2.getEntity();
//        try {
//            reader = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        StringBuffer result = new StringBuffer();
//        String line = "";
//        try {
//            while ((line = reader.readLine()) != null){
//                result.append(line);
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    //        try {
    //           obj = new URL("https://egrul.nalog.ru/");
    //
    //        } catch (MalformedURLException e) {
    //            e.printStackTrace();
    //        }
    //
    //        try {
    //            conn = (HttpURLConnection) obj.openConnection();
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            conn.setRequestMethod("POST");
    //        } catch (ProtocolException e) {
    //            e.printStackTrace();
    //        }
    //        conn.setRequestProperty("kind", "ul");
    //        conn.setRequestProperty("srchUl","ogrn");
    //        conn.setRequestProperty("ogrninnul",greeting.getInn());
    //        conn.setRequestProperty("srchFl","ogrn");
    //        conn.setRequestProperty("captcha",greeting.getCaptcha());
    //        conn.setRequestProperty("captchaToken",captchaToken);
    //        conn.setDoOutput(true);
    //        conn.setUseCaches(false);
    //        try {
    //            wr = new DataOutputStream(conn.getOutputStream());
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }




    //        conn = Jsoup.connect("https://egrul.nalog.ru/");
    //        conn.data("kind", "ul");
    //        conn.data("srchUl", "ogrn");
    //        conn.data("ogrninnul", greeting.getInn());
    //        conn.data("srchFl", "ogrn");
    //        conn.data("captcha", greeting.getCaptcha());
    //        conn.data("captchaToken", captchaToken);
    //        conn.method(Connection.Method.POST);



            //response = conn.url("https://egrul.nalog.ru/").response();


            //Element resultContent = webPage.getElementById("resultContent");
            //Elements tables = webPage.select("table");
            //Elements tables = webPage.select("table");
            //Element table = tables.get(0);
            //Element row = table.select("tr").get(0);
            //System.out.println(stringBuilder.toString());
        return "result";





    }
}
