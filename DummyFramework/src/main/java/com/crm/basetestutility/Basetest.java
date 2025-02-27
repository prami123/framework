package com.crm.basetestutility;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.crm.generic.fileUtility.ExcelUtility;
import com.crm.generic.fileUtility.PropFileutility;
import com.crm.generic.webdriverUtility.JavaUtility;
import com.crm.generic.webdriverUtility.UtilityclassObject;
import com.crm.generic.webdriverUtility.WebdriverUtility;
import com.crm.objectrepository.Homepage;
import com.crm.objectrepository.Loginpage;

public class Basetest {
	public WebDriver driver = null;
	public static WebDriver sdriver;
	public ExcelUtility eutil = new ExcelUtility();
	public JavaUtility jutil = new JavaUtility();
	public PropFileutility putil = new PropFileutility();
	public WebdriverUtility wutil = new WebdriverUtility();
//	public static ExtentReports report;
	


	@BeforeMethod (groups={"regression" ,"smoke"})
	public void configBM() throws IOException {
		String url = putil.getDataFromPropertyfile("url");
		String username = putil.getDataFromPropertyfile("username");
		String pwd = putil.getDataFromPropertyfile("password");
		Loginpage lp = new Loginpage(driver);
		lp.login(url, username, pwd);
	}

	@AfterMethod (groups={"regression" ,"smoke"})
	public void configAM() {
		Homepage hp = new Homepage(driver);
		hp.logout();
	}
	@Parameters("browser")
	@BeforeClass (groups={"regression" ,"smoke"})
	public void configBC(@Optional("chrome") String browser) throws IOException {
//		String Browsername = putil.getDataFromPropertyfile("browser");
		String Browsername = browser;
		if (Browsername.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (Browsername.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}else if (Browsername.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		sdriver=driver;
		UtilityclassObject.setDriver(driver);
		driver.manage().window().maximize();	

	}

	@AfterClass (groups={"regression" ,"smoke"})
	public void configAC() {
		driver.quit();
	}

	@BeforeSuite 
	public void configAS() throws SQLException {
//		dutil.getconnectionToDB();
		System.out.println("DB connection done");
	
	}
	
	@AfterSuite (groups={"regression" ,"smoke"},alwaysRun =true)
	public void configBS() throws SQLException {
		
		System.out.println("DB connection closed");
//		report.flush();
	}

	
}
