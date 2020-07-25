package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ElementUtil;

public class LoginPage extends BasePage {
	
	WebDriver driver;
	ElementUtil elementUtil;
	
	//Locators
	By emailId = By.id("username");             
	By password = By.id("password");           
	By loginBtn = By.id("loginBtn");       
	By signUp = By.linkText("Sign up"); 
	By loginErrorText = By.cssSelector("div.private-alert__inner");
	
	//Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	//Actions  == means methods
	public String getPageTitle() {
		elementUtil.waitForTitlePresent(AppConstants.LOGIN_PAGE_TITLE);
		return elementUtil.doGetPageTitle();
	}
	
	public boolean checkSignUpLink() {
		//elementUtil.waitForElementVisible(signUp);
		return elementUtil.doIsDisplayed(signUp);
	}
	
	    //this method provide to connect home page
	public HomePage doLogin(Credentials userCred) {  
		//elementUtil.waitForElementPresent(emailId);
		elementUtil.doSendKeys(emailId, userCred.getAppUsername());
		elementUtil.doSendKeys(password, userCred.getAppPassword());
		elementUtil.doClick(loginBtn);
		
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
		
		return new HomePage(driver);
	}

	public boolean checkLoginErrorMassage() {
		return elementUtil.doIsDisplayed(loginErrorText);
	}
	
	
	
	

}
