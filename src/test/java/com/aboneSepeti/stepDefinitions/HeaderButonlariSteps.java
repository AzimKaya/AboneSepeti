package com.aboneSepeti.stepDefinitions;

import com.aboneSepeti.pages.HomePage;
import com.aboneSepeti.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HeaderButonlariSteps {
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    private void analyzePageStructure() {
        WebDriver driver = Driver.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Sayfanın HTML yapısını yazdır
        String html = (String) js.executeScript("return document.documentElement.outerHTML");
        System.out.println("Sayfa HTML:");
        System.out.println(html);

        // Tüm butonları ve linkleri bul
        List<WebElement> elements = driver.findElements(By.cssSelector("button, a, [role='button'], span"));
        System.out.println("\nTüm elementler ve özellikleri:");
        for (WebElement element : elements) {
            try {
                String text = element.getText().trim();
                String tag = element.getTagName();
                String href = element.getAttribute("href");
                String onclick = element.getAttribute("onclick");
                String classes = element.getAttribute("class");
                
                if (!text.isEmpty()) {
                    System.out.println("Element:");
                    System.out.println("- Text: " + text);
                    System.out.println("- Tag: " + tag);
                    System.out.println("- Href: " + href);
                    System.out.println("- Onclick: " + onclick);
                    System.out.println("- Classes: " + classes);
                    System.out.println();
                }
            } catch (StaleElementReferenceException e) {
                // Element artık DOM'da yok, atla
                continue;
            }
        }
    }

    private void clickButton(String buttonText) throws InterruptedException {
        WebDriver driver = Driver.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Sayfanın yüklenmesini bekle
        Thread.sleep(2000);
        
        // Sayfa yapısını analiz et
        analyzePageStructure();
        
        // Tüm olası seçicileri dene
        String[] xpaths = {
            "//*[contains(text(),'" + buttonText + "')]",
            "//*[text()='" + buttonText + "']",
            "//a[contains(.,'" + buttonText + "')]",
            "//button[contains(.,'" + buttonText + "')]",
            "//div[contains(.,'" + buttonText + "')]",
            "//span[contains(.,'" + buttonText + "')]"
        };
        
        for (String xpath : xpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                for (WebElement element : elements) {
                    try {
                        // Elementi görünür yap
                        js.executeScript("arguments[0].scrollIntoView(true);", element);
                        Thread.sleep(500);
                        
                        // Elementi tıklanabilir yap
                        js.executeScript(
                            "arguments[0].style.border='2px solid red';" +
                            "arguments[0].style.backgroundColor='yellow';", 
                            element);
                        Thread.sleep(500);
                        
                        // Tıklamayı dene
                        try {
                            element.click();
                        } catch (Exception e1) {
                            try {
                                js.executeScript("arguments[0].click();", element);
                            } catch (Exception e2) {
                                // Son çare: href'i direkt aç
                                String href = element.getAttribute("href");
                                if (href != null && !href.isEmpty()) {
                                    driver.get(href);
                                }
                            }
                        }
                        
                        // URL'nin değişmesini bekle
                        Thread.sleep(2000);
                        return;
                        
                    } catch (Exception e) {
                        System.out.println("Element tıklanamadı: " + e.getMessage());
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("XPath başarısız: " + xpath);
                continue;
            }
        }
        
        throw new RuntimeException("Buton bulunamadı veya tıklanamadı: " + buttonText);
    }

    @When("kullanıcı Özellikler butonuna tıklar")
    public void kullaniciOzelliklerButonunaTiklar() throws InterruptedException {
        // Çerez butonuna tıkla
        try {
            WebElement cerezBtn = Driver.getDriver().findElement(By.cssSelector("button.px-8.bg-primary-500"));
            cerezBtn.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            // Çerez butonu yoksa devam et
        }

        clickButton("Özellikler");
    }

    @Then("Özellikler sayfasının açıldığını doğrula")
    public void ozelliklerSayfasininAcildiginiDogrula() throws InterruptedException {
        Thread.sleep(2000);
        String url = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue("URL özellikler içermiyor: " + url, 
            url.contains("/ozellikler") || url.contains("/features"));
        Driver.getDriver().get("https://abonesepeti.com/tr");
        Thread.sleep(2000);
    }

    @When("kullanıcı Çözümler butonuna tıklar")
    public void kullaniciCozumlerButonunaTiklar() throws InterruptedException {
        clickButton("Çözümler");
    }

    @Then("Çözümler sayfasının açıldığını doğrula")
    public void cozumlerSayfasininAcildiginiDogrula() throws InterruptedException {
        Thread.sleep(2000);
        String url = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue("URL çözümler içermiyor: " + url,
            url.contains("/cozumler") || url.contains("/solutions"));
        Driver.getDriver().get("https://abonesepeti.com/tr");
        Thread.sleep(2000);
    }

    @When("kullanıcı Blog butonuna tıklar")
    public void kullaniciBlogButonunaTiklar() throws InterruptedException {
        clickButton("Blog");
    }

    @Then("Blog sayfasının açıldığını doğrula")
    public void blogSayfasininAcildiginiDogrula() throws InterruptedException {
        Thread.sleep(2000);
        String url = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue("URL blog içermiyor: " + url,
            url.contains("/blog"));
        Driver.getDriver().get("https://abonesepeti.com/tr");
        Thread.sleep(2000);
    }

    @When("kullanıcı Biz Kimiz butonuna tıklar")
    public void kullaniciBizKimizButonunaTiklar() throws InterruptedException {
        clickButton("Biz Kimiz");
    }

    @Then("Biz Kimiz sayfasının açıldığını doğrula")
    public void bizKimizSayfasininAcildiginiDogrula() {
        String url = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue("URL biz-kimiz içermiyor: " + url,
            url.contains("/biz-kimiz") || url.contains("/about-us"));
        Driver.closeDriver();
    }
}
