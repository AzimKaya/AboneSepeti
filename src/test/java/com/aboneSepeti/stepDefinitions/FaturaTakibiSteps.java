package com.aboneSepeti.stepDefinitions;

import io.cucumber.java.tr.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aboneSepeti.pages.HomePage;
import com.aboneSepeti.utilities.Driver;
import java.time.Duration;
import java.util.List;

public class FaturaTakibiSteps {
    private HomePage homePage = new HomePage();
    private WebDriver driver = Driver.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Diyelimki("kullanıcı AboneSepeti ana sayfasında")
    public void kullaniciAnaSayfada() {
        driver.get("https://abonesepeti.com/tr");
        System.out.println("Ana sayfa açıldı. URL: " + driver.getCurrentUrl());
        
        // Sayfanın yüklenmesini bekle
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    @Ve("kullanıcı Aboneliklerini Yönet butonuna tıklar")
    public void kullaniciAbonelikleriniYonetButonunaTiklar() {
        try {
            // Çerez kabul butonu görünür ve tıklanabilir olana kadar bekle
            try {
                WebElement cerezBtn = wait.until(ExpectedConditions.elementToBeClickable(homePage.cerezKabulBtn));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cerezBtn);
                Thread.sleep(1000); // Çerez banner'ının kaybolmasını bekle
            } catch (TimeoutException e) {
                System.out.println("Çerez kabul butonu bulunamadı veya zaten kabul edilmiş olabilir");
            }

            // Tüm linkleri kontrol et
            List<WebElement> links = driver.findElements(By.tagName("a"));
            WebElement targetLink = null;
            
            for (WebElement link : links) {
                String href = link.getAttribute("href");
                if (href != null && href.contains("aboneliklerini-yonet")) {
                    targetLink = link;
                    System.out.println("Hedef link bulundu: " + href);
                    break;
                }
            }

            if (targetLink == null) {
                throw new RuntimeException("Aboneliklerini Yönet linki bulunamadı!");
            }

            // Linke scroll yap ve tıkla
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", targetLink);
            Thread.sleep(1000);

            // JavaScript ile tıkla
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetLink);
            Thread.sleep(1000);

        } catch (Exception e) {
            System.out.println("Aboneliklerini Yönet butonuna tıklanamadı: " + e.getMessage());
            throw new RuntimeException("Aboneliklerini Yönet butonuna tıklanamadı!");
        }
    }

    @Ozaman("Fatura Takibi sayfasının açıldığını doğrula")
    public void faturaTakibiSayfasininAcildiginiDogrula() {
        try {
            // Sayfanın yüklenmesini bekle
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
            
            // URL kontrolü
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("fatura") && !currentUrl.contains("abonelik")) {
                throw new RuntimeException("Yanlış sayfadayız: " + currentUrl);
            }
            
            System.out.println("Fatura Takibi sayfası başarıyla açıldı");
        } catch (Exception e) {
            System.out.println("Fatura Takibi sayfası açılamadı: " + e.getMessage());
            throw new RuntimeException("Fatura Takibi sayfası açılamadı!");
        }
    }

    @Ozaman("tarayıcıyı kapat")
    public void tarayiciyiKapat() {
        try {
            Driver.closeDriver();
            System.out.println("Tarayıcı başarıyla kapatıldı");
        } catch (Exception e) {
            System.out.println("Tarayıcı kapatılırken hata oluştu: " + e.getMessage());
        }
    }
}
