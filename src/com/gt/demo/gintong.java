package com.gt.demo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class gintong {
	@SuppressWarnings("rawtypes")
	public static void main(String args[]) throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.BROWSER_NAME, "");
        cap.setCapability("platformName", "Android");
        cap.setCapability("deviceName", "Android Emulator");
        cap.setCapability("platformVersion", "5.1");
        cap.setCapability("appPackage", "com.tr");
        cap.setCapability("appActivity", "com.tr.ui.user.SplashActivity");

        AndroidDriver dr = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
        
//        dr.findElement(By.id("com.tr:id/image_docking")).click();        

//        dr.findElement(By.id("com.android.calculator2:id/digit9")).click();
    }

}
