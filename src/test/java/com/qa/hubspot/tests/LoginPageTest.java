package com.qa.hubspot.tests;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Epic - 101 : create login features")
@Feature("US - 501 : Create test for login on HubSpot")
public class LoginPageTest {
	
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;

	Logger log = Logger.getLogger(LoginPageTest.class);
	
	@BeforeMethod(alwaysRun= true)
	@Parameters(value= {"browser"})
	public void setUp(String browser) {
		String browserName = null;
		basePage = new BasePage();
		prop = basePage.init_properties();
		
		if(browser.equals(null)) {
			browserName = prop.getProperty("browser");
		}else {
			browserName = browser;
		}
		
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		log.info("url is launched "+ prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}
	
    @Test(priority=1, groups="sanity", description="get page title as HubSpot Login", enabled=true)
	@Description("Verify Login Page title")
	@Severity(SeverityLevel.NORMAL)
	public void verifyPageTitletest() {
		log.info("starting --------------->>>> verifyLoginPageTest");
		String title = loginPage.getPageTitle();
		System.out.println("login page title is " + title);
		Assert.assertEquals(title, "HubSpot Login");
		log.info("ending ---------------->>>> verifyLoginPageTest");
		log.warn("some warning");
		log.error("some error");
		log.fatal("fatal error");
		
	}
	
	@Test(priority=2, description="sign up link is displayed or not", enabled=true)
	@Description("Verify sign up link")
	@Severity(SeverityLevel.NORMAL)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}
	
	@Test(priority=3, description="inavalid username and password for the Login page", enabled=false)
	@Description("Login page credentials")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		HomePage homePage = loginPage.doLogin(userCred);
		String accountName = homePage.getLoggedInUserAccountName();
		System.out.println("logged in accountName name " + accountName);
		Assert.assertEquals(accountName	, "siliconelabs.com");  //for verification to use Assert
	}
	
	@DataProvider
	public Object[][] getLoginInvalidData(){
		Object  data [][] = {{"bill@gmail.com", "test12345"},
				            {"jimy@gmail.com", "test123"},
				            {"yumyt@gmail.com", "test12346"},
				            {"yummy@gmail.com", "234er32"},
				            {"adem@gmail.com", "5432ws43"}};
		
		return data;
	}
	
	@Test(priority=4, dataProvider= "getLoginInvalidData", enabled=false)
	public void login_invalidTestCase(String username, String pwd) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMassage());
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.quit();
	}
	

}
