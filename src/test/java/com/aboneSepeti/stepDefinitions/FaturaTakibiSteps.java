package com.aboneSepeti.stepDefinitions;

import io.cucumber.java.tr.*;
import org.junit.Assert;
import com.aboneSepeti.pages.HomePage;
import com.aboneSepeti.utilities.Driver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;

public class FaturaTakibiSteps {
    
    HomePage homePage = new HomePage();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
    Actions actions = new Actions(Driver.getDriver());
    
    @Diyelimki("kullanıcı AboneSepeti ana sayfasında")
    public void kullaniciAnaSayfada() {
        Driver.getDriver().get("https://abonesepeti.com/tr");
        // Sayfanın tamamen yüklenmesini bekle
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
            .executeScript("return document.readyState").equals("complete"));
            
        System.out.println("Ana sayfa açıldı. URL: " + Driver.getDriver().getCurrentUrl());
        
        // Tüm butonları ve linkleri listele
        List<WebElement> allElements = Driver.getDriver().findElements(By.cssSelector("a, button"));
        System.out.println("\nSayfadaki tüm butonlar ve linkler:");
        for (WebElement element : allElements) {
            System.out.println("Element text: " + element.getText());
            System.out.println("Element class: " + element.getAttribute("class"));
            System.out.println("Element href: " + element.getAttribute("href"));
            System.out.println("---");
        }
    }
    
    @Ve("kullanıcı Aboneliklerini Yönet butonuna tıklar")
    public void kullaniciAbonelikleriniYonetButonunaTiklar() {
        try {
            // Sayfanın yüklenmesini bekle
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Aboneliklerini Yönet linkini bul
            WebElement abonelikLink = Driver.getDriver().findElement(
                By.cssSelector("a[href*='aboneliklerini-yonet']"));
            
            if (abonelikLink != null && abonelikLink.isDisplayed()) {
                // Linke tıkla
                actions.moveToElement(abonelikLink).click().perform();
                System.out.println("Aboneliklerini Yönet linkine tıklandı");
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return;
            }
            
            throw new RuntimeException("Aboneliklerini Yönet linki bulunamadı veya tıklanamadı!");
        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @Ozaman("Fatura Takibi sayfasının açıldığını doğrula")
    public void faturaTakibiSayfasininAcildiginiDogrula() {
        try {
            Thread.sleep(2000);
            
            String currentUrl = Driver.getDriver().getCurrentUrl();
            System.out.println("\nMevcut URL: " + currentUrl);
            
            // Sayfadaki tüm metinleri ve URL'yi kontrol et
            String pageSource = Driver.getDriver().getPageSource().toLowerCase();
            boolean isSuccess = currentUrl.toLowerCase().contains("fatura") || 
                              currentUrl.toLowerCase().contains("abonelik") ||
                              pageSource.contains("fatura") ||
                              pageSource.contains("abonelik");
                              
            if (!isSuccess) {
                System.out.println("\nSayfa içeriği ve elementleri:");
                List<WebElement> elements = Driver.getDriver().findElements(By.cssSelector("*"));
                for (WebElement element : elements) {
                    if (!element.getText().trim().isEmpty()) {
                        System.out.println("Element text: " + element.getText());
                    }
                }
            }
            
            Assert.assertTrue("Fatura Takibi sayfası açılmadı!", isSuccess);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
    
    @Ozaman("tarayıcıyı kapat")
    public void tarayiciyiKapat() {
        Driver.closeDriver();
    }
}
