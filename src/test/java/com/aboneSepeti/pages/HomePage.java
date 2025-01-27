package com.aboneSepeti.pages;

import com.aboneSepeti.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public HomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "button.px-8.bg-primary-500")
    public WebElement cerezKabulBtn;

    @FindBy(xpath = "//button[contains(text(), 'Özellikler')]")
    public WebElement ozelliklerBtn;

    @FindBy(xpath = "//button[contains(text(), 'Çözümler')]")
    public WebElement cozumlerBtn;

    @FindBy(xpath = "//button[contains(text(), 'Blog')]")
    public WebElement blogBtn;

    @FindBy(xpath = "//button[contains(text(), 'Biz Kimiz')]")
    public WebElement bizKimizBtn;

    @FindBy(css = "a[href*='/uygulamaya-git']")
    public WebElement uygulamayaGitBtn;

    @FindBy(css = "a[href*='/aboneliklerini-yonet']")
    public WebElement abonelikleriniYonetBtn;

    @FindBy(css = "h1")
    public WebElement pageTitle;
}
