package com.LamdaTest.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LambdaTest2 {

public WebDriver driver;
	
	public final String userName  = "Polamada.Jayasaivardhan";
	public final String accessKey = "zhwVrW3mU0VMX8cdhE8N2SjBdksvkZxIveF49eqKDOEGPn6amD";
	
	public String hubURL= "hub.lambdatest.com/wd/hub";
	public String url="https://www.lambdatest.com/";
	
	@Test
	public void AutomationAssignmentTask() throws MalformedURLException {
		
		//capabilities
		EdgeOptions browserOptions = new EdgeOptions();
		browserOptions.setPlatformName("macOS Sierra");
		browserOptions.setBrowserVersion("87.0");
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username", userName);
		ltOptions.put("accessKey", accessKey);
		ltOptions.put("geoLocation", "IN");
		ltOptions.put("visual", true);
		ltOptions.put("video", true);
		ltOptions.put("network", true);
		ltOptions.put("console", true);
		ltOptions.put("selenium_version", "4.0.0");
		ltOptions.put("build", "Automation Test Assignment");
		ltOptions.put("project", "Assignment Task");
		ltOptions.put("name", "LambdaTest in Edge_MacOS");
		ltOptions.put("w3c", true);
		ltOptions.put("plugin", "java-testNG");
		browserOptions.setCapability("LT:Options", ltOptions);
		
		String hub="https://"+userName+":"+accessKey+"@"+hubURL;
		
		
		//remote driver instance
		driver= new RemoteWebDriver(new URL(hub),browserOptions);
	    //launching URL
		driver.get(url);
		driver.manage().window().maximize();
		
		JavascriptExecutor js= (JavascriptExecutor) driver;
		
		// explicit wait until all the elements in the DOM are available
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
				
		//scroll to web element
		WebElement ele=driver.findElement(By.xpath("//*[text()='See All Integrations']"));
		String expectedNavigatedURL=ele.getAttribute("href");
		js.executeScript("arguments[0].scrollIntoView({block: 'center'})", ele);
		
		//opening the link in new tab with a click
		Actions action=new Actions(driver);
		action.moveToElement(ele).keyDown(Keys.COMMAND).click().build().perform();	
		
		//store window handles, print in console and switch to child window
		Set<String> windowHandles=driver.getWindowHandles();
		String parentWindow=driver.getWindowHandle();

		System.out.println(windowHandles);
		
		for(String windowHandle: windowHandles) {
			if(!parentWindow.equals(windowHandle)) {
				driver.switchTo().window(windowHandle);
			}
		}
		
		String actualNavigatedURL=driver.getCurrentUrl();
		
		//assertion check on child url
		Assert.assertEquals(actualNavigatedURL, expectedNavigatedURL);
		
		//Close the current browser window
		driver.close();
		
		//kill driver session
		driver.quit();
        
	}
	
}
