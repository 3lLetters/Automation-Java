package moolya.sunnxt.webTests;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import moolya.sunnxt.pages.webpages.HomePage;
import moolya.sunnxt.pages.webpages.SignInPage;
import moolya.sunnxt.pages.webpages.W_BasePage;
import moolya.sunnxt.pages.webpages.WelcomePage;

public class W_BaseTest 
{

public WebDriver wdriver;
	
	W_BasePage basepage;

	ATUTestRecorder recorder;
	private WelcomePage wcp;
	private SignInPage sip;
	private HomePage hp;
	private String uniqueValue = "Login001";
	
	@BeforeClass
	public void setUp() throws IOException, ATUTestRecorderException, EncryptedDocumentException, InvalidFormatException{
		String dir = System.getProperty("user.dir");
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		recorder = new ATUTestRecorder(dir+"\\script-videos\\", "Verify-Order-Summary-" + dateFormat.format(date), false);
		// To start video recording.
		basepage = new W_BasePage(wdriver);
		recorder.start();
		wdriver = basepage.launchWebApp();
		
		
		wcp = new WelcomePage(wdriver);
		sip = wcp.Click_SignIn();
		hp = sip.do_login(uniqueValue );
		
	}
	
	
	
	@AfterMethod
	public void catchExceptions(ITestResult result) throws IOException, InterruptedException 
	{    
		String dir = System.getProperty("user.dir");
		String[] clsParts = result.getInstanceName().split("\\.");
		String clsName = clsParts[(clsParts.length)-1];
		if(!result.isSuccess()){            
			File scrFile = ((TakesScreenshot)wdriver).getScreenshotAs(OutputType.FILE);
			String file =dir+"/screenshots/"+clsName+".png";
			try {
				FileUtils.copyFile(scrFile, new File(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@AfterClass
	public void tearDown() throws ATUTestRecorderException{
		wdriver.close();
		recorder.stop();
	}
	
}
