package com.aboneSepeti.stepDefinitions;

import com.aboneSepeti.utilities.Driver;
import io.cucumber.java.tr.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HemenUcretsizIndirSteps {
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    @Diyelimki("^kullanıcı Abone Sepeti anasayfasını açar$")
    public void kullaniciAnaSayfayiAcar() {
        Driver.getDriver().get("https://www.abonesepeti.com");
    }

    @Ozaman("^Hemen Ücretsiz İndir butonuna tıkla$")
    public void hemenUcretsizIndirButonunaTikla() {
        WebElement indirButonu = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'Hemen Ücretsiz İndir') or contains(@class, 'download-button')]")));
        indirButonu.click();
    }

    @Ve("^sayfayı kapat$")
    public void sayfayiKapat() {
        Driver.closeDriver();
    }
}
