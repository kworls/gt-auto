package com.gintong.android.test;
import com.gintong.android.utils.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;



public class gintong {
	public String port="4723";
	
//	public String udid="DU2TAN148U004505";
	public String udid="NX511J";
//	public String udid="AYNB9SOJAYYLTCLF";
	public static String host="jtmobile.gintong.com";
	public String username="18664900944";
	public String password="121212";
	public String text="werwerwerwersdfsdf1";
	private AndroidDriver<WebElement> driver;
	
	
	
	
	 public void login() {		 
	        
	    	System.out.println("确认是否登录...");
	    	
	    	try{    		
	        
	    		if (isLoginPresent(driver, 5)) {	        	
	        	
	        		// wait for 20s
		        	 // find login userName and password editText
		        
	    			System.out.println("需要登录...");
		        	WebElement loginButton;
			        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			        WebElement userNameEt=driver.findElement(By.id("com.tr:id/userNameEt"));       
			       
			        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			        WebElement passwordEt=driver.findElement(By.id("com.tr:id/passwordEt"));
			        
			        userNameEt.sendKeys(username);
			        passwordEt.sendKeys(password);
			       
			               	
			        System.out.println("登录...");	        	
		            loginButton = driver.findElement(By
		                    .id("com.tr:id/loginTv"));
		            loginButton.click();
		            
		            // find keyword 首页 and verify it is display		        
		        
		            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			        Assert.assertTrue(driver.findElement(By.id("com.tr:id/actionBartitleTv")).isDisplayed());
	        	
	        
	    		}

	        else {
	        	System.out.println("已登录 跳过登录...");
	        	
//	            Assert.assertTrue(true);
	        }
	    	
        	}catch(Exception e){
        		
        		System.out.println("忽略登录 ...");
        	}
	        	

	    }

	    public boolean isLoginPresent(AndroidDriver driver, int timeout) {
	        boolean isPresent = new AndroidDriverWait(driver, timeout).until(
	                new ExpectedCondition<WebElement>() {
	                    public WebElement apply(AndroidDriver d) {
	                        return d.findElement(By
	                                .id("com.tr:id/loginTv"));
	                    }

	                }).isDisplayed();
	        return isPresent;
	    }
	    
	    
	    
	    public void messageSending(){
	    	
			 
			 
			 String  username="44235475@qq.com";			
			 String  password="MTExMTExMQ==";
			
			 http ht=new http();
			 ht.setProxyflag(true);
			 ht.setUsername(username);
			 ht.setPassword(password);
			 ht.setHost(host);
			 
			 

			 ht.sendText(ht.getUserInfo(), "2898", text);
	    	
	    }
	    
	    @Test
		public void gtMessage() throws InterruptedException {
	    	
	    	
	    	System.out.println("寻找并打开畅聊...");
			WebElement gtMessage=driver.findElement(By.id("com.tr:id/activity_inlet_gam_fl"));
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			gtMessage.click();
			
//			
			try{
//				driver.scrollTo("testmessage").click();	
				driver.findElementByName("testmessage").click();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}catch(Exception e){
				
			}

			
			this.messageSending();
			

			
			try{
				WebElement messageEdit=driver.findElement(By.name(text));
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}catch(Exception e){
				
				WebElement messageEdit=driver.findElement(By.id("com.tr:id/textEt"));
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//				messageEdit.click();
				messageEdit.sendKeys("没收到");
				
				
			}
//			WebElement messageEdit=driver.findElement(By.id("com.tr:id/textEt"));
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
////			messageEdit.click();
//			messageEdit.sendKeys("乱码");
//			
//			
//			WebElement messageSend=driver.findElement(By.id("com.tr:id/sendIv"));
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			messageSend.click();		
				
			
		}

	    
	@BeforeMethod
	public void beforeMethod(){
		login();
	}
	
	@BeforeClass

	public void beforeClass() throws MalformedURLException{
		System.out.println(port + udid);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName","Android Emulator");
		capabilities.setCapability("automationName","Appium");
		capabilities.setCapability("platformVersion", "5.1");
		capabilities.setCapability("udid", udid);
		capabilities.setCapability("appPackage", "com.tr");
		capabilities.setCapability("appActivity", "com.tr.ui.user.SplashActivity");
		
		capabilities.setCapability("unicodeKeyboard", "True");  
		capabilities.setCapability("resetKeyboard", "True"); 
		
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);

	}
		
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	

}
