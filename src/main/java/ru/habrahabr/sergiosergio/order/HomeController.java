package ru.habrahabr.sergiosergio.order;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {

    private String imageSourse;//путь до файла капчи
    private String captchaToken;// токен капчи
    private Greeting greeting = new Greeting();//класс для хранения введенных инн и капчи
    private Document webPage;//копия страницы для парсера
    private CloseableHttpResponse response2;//http responce
    private Responce result;//хранит pojo вариант json ответа
    private String json;//json ответ
    private Gson gson = new Gson();
    @Value("${config.excellogfile}")
    private String excelLogFile;
    private FileInputStream excelFile;
    private Workbook workbook;
    org.apache.poi.ss.usermodel.Row currentRow = null;




    @GetMapping("/greeting")
    public String greetingForm(Model model){

         /*
        Секция чтения файлов
         */
//        try {
//            excelFile = new FileInputStream(new File("D:\\tools\\Java\\судебный приказ\\06-09-2017_18-24-46\\Журнал движения 2017.xls"));
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            workbook = new HSSFWorkbook(excelFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Sheet datatypeSheet = workbook.getSheetAt(0);
//        Iterator <org.apache.poi.ss.usermodel.Row> iterator = datatypeSheet.iterator();
//        Boolean cycleExitFlag = false;
//        int j = 0;
//        while(iterator.hasNext()){
//
//            currentRow = iterator.next();
//            Iterator<Cell> cellIterator = currentRow.iterator();
//            greeting.getLogContent().add(j, new LogContentUnit());
//
//            for (int i = 0; i < 5; i++){
//                Cell currentCell = cellIterator.next();
//                if (i == 5 && currentCell.getStringCellValue().equals("")){
//                    cycleExitFlag = true;
//                    break;
//                }
//
//                if (i == 0){
//                    greeting.getLogContent().get(j).setCaseNumber(currentCell.getStringCellValue());
//
//                }
//
//                if (i == 3){
//                    greeting.getLogContent().get(j).setArbNumber(currentCell.getStringCellValue());
//                }
//
//            }
//        }
//        try {
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            excelFile.close();
//        } catch (IOException e) {
//
//        }
//

        /*
        Секция парсера сайта
         */
        //model.addAttribute("logcontent", logContent);

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
        model.addAttribute("imageSource", imageSourse );




        return "greeting";
    }
    @PostMapping(value = "/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {

//        System.out.println(greeting.getCaptcha());
//        System.out.println(greeting.getInn());
//        System.out.println(captchaToken);
//        System.out.println(greeting.getTheChosenCase());

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
        String finalString = row.getNAME()+" "+ row.getADRESTEXT() +" "+ row.getOGRN() +" "+ row.getINN() +" "+ row.getDTREG();
        model.addAttribute("finalstring", finalString);

        //System.out.println(finalString);



        return "result";
    }

}
