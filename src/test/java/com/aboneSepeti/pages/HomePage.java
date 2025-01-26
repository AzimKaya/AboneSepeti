package com.aboneSepeti.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aboneSepeti.utilities.Driver;

public class HomePage {
    
    public HomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
    
    @FindBy(linkText = "Aboneliklerini Yönet")
    public WebElement abonelikleriniYonetBtn;
    
    @FindBy(css = "h1.page-title")
    public WebElement pageTitle;
}
