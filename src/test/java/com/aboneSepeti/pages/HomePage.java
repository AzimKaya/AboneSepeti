package com.aboneSepeti.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aboneSepeti.utilities.Driver;

public class HomePage {
    
    public HomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
    
    @FindBy(css = "button.px-8.bg-primary-500")
    public WebElement cerezKabulBtn;
    
    @FindBy(css = "a[href*='ozellikler/aboneliklerini-yonet']")
    public WebElement abonelikleriniYonetBtn;
    
    @FindBy(css = "h1")
    public WebElement faturaTakibiBaslik;
    
    @FindBy(xpath = "//a[text()='Uygulamaya Git']")
    public WebElement uygulamayaGitBtn;
}
