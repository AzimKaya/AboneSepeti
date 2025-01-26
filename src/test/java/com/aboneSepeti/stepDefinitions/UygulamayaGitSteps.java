package com.aboneSepeti.stepDefinitions;

import io.cucumber.java.tr.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aboneSepeti.pages.HomePage;
import com.aboneSepeti.utilities.Driver;
import java.time.Duration;
import java.util.Set;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class UygulamayaGitSteps {
    private HomePage homePage = new HomePage();
    private WebDriver driver = Driver.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    private String anaSayfaWindowHandle;

    @Ve("kullanıcı Uygulamaya Git butonuna tıklar")
    public void kullaniciUygulamayaGitButonunaTiklar() {
        try {
            // Çerez kabul butonu görünür ve tıklanabilir olana kadar bekle
            try {
                WebElement cerezBtn = wait.until(ExpectedConditions.elementToBeClickable(homePage.cerezKabulBtn));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cerezBtn);
                Thread.sleep(2000);
            } catch (TimeoutException e) {
                System.out.println("Çerez kabul butonu bulunamadı veya zaten kabul edilmiş olabilir");
            }

            // Ana sayfa window handle'ını kaydet
            anaSayfaWindowHandle = driver.getWindowHandle();

            // Alternatif olarak tüm linkleri kontrol et
            List<WebElement> links = driver.findElements(By.tagName("a"));
            WebElement targetLink = null;
            
            for (WebElement link : links) {
                try {
                    String text = link.getText().trim();
                    if (text.equals("Uygulamaya Git")) {
                        targetLink = link;
                        System.out.println("Hedef link bulundu: " + text);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            if (targetLink == null) {
                throw new RuntimeException("Uygulamaya Git linki bulunamadı!");
            }

            // Linke scroll yap
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", targetLink);
            Thread.sleep(2000);

            // CTRL tuşuna basılı tutarak tıkla
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL)
                   .click(targetLink)
                   .keyUp(Keys.CONTROL)
                   .build()
                   .perform();
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("Uygulamaya Git butonuna tıklanamadı: " + e.getMessage());
            throw new RuntimeException("Uygulamaya Git butonuna tıklanamadı!");
        }
    }

    @Ozaman("uygulama sayfasının açıldığını doğrula")
    public void uygulamaSayfasininAcildiginiDogrula() {
        try {
            // Yeni sekmeye geç
            Set<String> windowHandles = driver.getWindowHandles();
            String yeniSekmeHandle = "";
            
            for (String handle : windowHandles) {
                if (!handle.equals(anaSayfaWindowHandle)) {
                    yeniSekmeHandle = handle;
                    break;
                }
            }
            
            if (yeniSekmeHandle.isEmpty()) {
                throw new RuntimeException("Yeni sekme açılmadı!");
            }
            
            // Yeni sekmeye geç
            driver.switchTo().window(yeniSekmeHandle);
            
            // Sayfanın yüklenmesini bekle
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
            
            // URL kontrolü
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Açılan sayfa URL'i: " + currentUrl);
            
            if (!currentUrl.contains("app.abonesepeti.com")) {
                throw new RuntimeException("Yanlış sayfadayız: " + currentUrl);
            }
            
            System.out.println("Uygulama sayfası başarıyla açıldı: " + currentUrl);
            
            // Sayfanın görülebilmesi için 3 saniye bekle
            Thread.sleep(3000);
            
            // Ana sekmeye geri dön
            driver.switchTo().window(anaSayfaWindowHandle);
            
        } catch (Exception e) {
            System.out.println("Uygulama sayfası açılamadı: " + e.getMessage());
            throw new RuntimeException("Uygulama sayfası açılamadı!");
        }
    }
}
