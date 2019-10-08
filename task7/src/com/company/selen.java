package com.company;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.*;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

class selen {

    static Map<String, Float> rate = new HashMap<String,Float>();

    public  static WebDriver seleniumFirefox() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\17524894\\IdeaProjects\\Education\\src\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "C:\\Users\\17524894\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
        ProfilesIni profileIni = new ProfilesIni();
        FirefoxProfile profile = profileIni.getProfile("default");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        profile.setPreference("webgl.max-contexts-per-principal",32);
        WebDriver driver = new FirefoxDriver(options);
        return driver;
    }

    private static void prnt(Map<String, Float> result){
        float total = 0;
        for(Map.Entry<String, Float> eac : result.entrySet()){
            System.out.println("Cost: " + eac.getValue() + " руб. link: " + eac.getKey());
            total += eac.getValue();
        }
        System.out.println("Average salary of java programmer: " + total/result.size());

    }

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = selen.seleniumFirefox();

        driver.get("https://hh.ru/search/vacancy?L_is_autosearch=false&area=1&clusters=true&enable_snippets=true&text=java&page=0");

        Map<String, Float> result = new HashMap<String, Float>();
        String js;
        WebElement element = null;
        do {
            List<WebElement> elementName = driver.findElements(By.className("vacancy-serp-item"));
            for (WebElement el : elementName) {

                try
                {
                    element = el.findElement(By.className("vacancy-serp-item__compensation"));
                }
                catch(Exception e)
                {
                   continue;
                 }

                result.put(el.findElement(By.cssSelector(".bloko-link.HH-LinkModifier")).getAttribute("href"), getCost(element.getText()));
                System.out.println("result:" + result.size() + " jobs find");
            }

            try {
                element = driver.findElement(By.className("saved-search-subscription-wrapper__closer"));

            }catch (Exception e){

            }
            if (element.isDisplayed()){
                element.click();
            }

            try {
                element = driver.findElement(By.cssSelector("HH-Supernova-RegionClarification-Confirm"));

            }catch (Exception e){

            }
            if (element.isDisplayed()){
                element.click();
            }

            try{
                element = driver.findElement(By.className("HH-Pager-Controls-Next"));
            }
            catch (Exception e){
                System.out.println("end of pars");
                break;
            }
            int elementPosition = element.getLocation().getY();
            js = String.format("window.scroll(0, %s)", elementPosition);
            ((JavascriptExecutor)driver).executeScript(js);
            element.click();

        } while (true);

        prnt(result);
    }

    private static float getCost (String wordCost){
        int cost;

        if (wordCost.indexOf('-') >= 0){
            float minCost = Integer.parseInt((wordCost.substring(0, wordCost.indexOf('-'))).replaceAll("[^\\d+]", ""));
            float maxCost = Integer.parseInt((wordCost.substring(wordCost.indexOf('-'), wordCost.length())).replaceAll("[^\\d+]", ""));
            return (minCost + maxCost) / 2 * toRUB(wordCost);
        }

        cost = Integer.parseInt(wordCost.replaceAll("[^\\d+]", ""));

        return cost * toRUB(wordCost);
    }

    private static float toRUB (String wordCost) {
        StringBuffer rateWord = new StringBuffer();

        for (int i = wordCost.lastIndexOf(" ") + 1; i < wordCost.length(); i++)
            rateWord.append(wordCost.charAt(i));
        if (rateWord.toString().equals("руб."))
            return 1;
        return  getRate(rateWord.toString());
    }

    private static float getRate(String currency){

        if(selen.rate.get(currency) != null){
            return selen.rate.get(currency);
        }

        WebDriver driver = selen.seleniumFirefox();

        driver.get("https://bankiros.ru/convert/" + currency.toLowerCase() + "-rub/1");
        WebElement sub = driver.findElement(By.id("cbr_second"));
        selen.rate.put(currency, Float.parseFloat(sub.getAttribute("value")));
        driver.quit();

        return selen.rate.get(currency);
//        return (float) 65;
    }
}
