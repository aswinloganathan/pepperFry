package project.pepperfry;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PepperFry {
	
    public static void main( String[] args ) throws InterruptedException, IOException {
    	
    	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
    	System.setProperty("webdriver.chrome.silentOutput", "true");
    	
    	WebDriver driver = new ChromeDriver();
    	driver.manage().window().maximize();
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	
    	WebDriverWait wait = new WebDriverWait(driver, 30);
    	
    	
    	//1) Go to https://www.pepperfry.com/
    	
    	driver.get("https://www.pepperfry.com/");
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@class='popup-close'])[5]"))).click();
   
 
    	//2) Mouseover on Furniture and click Office Chairs under Chairs
    	
    	Actions action = new Actions(driver);
    	action.moveToElement(driver.findElement(By.xpath("(//a[text()='Furniture'])[1]"))).perform();
    	action.pause(2000);
    	action.moveToElement(driver.findElement(By.xpath("//a[text()='Office Chairs']"))).click().build().perform();
    	Thread.sleep(3000);
    	
    	//3) click Executive Chairs
    	
    	driver.findElement(By.xpath("//h5[text()='Executive Chairs']")).click();
    	Thread.sleep(3000);    	
    	
		//4) Change the minimum Height as 50 in under Dimensions
    	
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("window.scrollBy(0,400)");
    	
    	Thread.sleep(3000);
    	
    	WebElement chairHeight = driver.findElement(By.xpath("//div[text()='Height']//parent::li/div[3]/input[1]"));
    	action.moveToElement(chairHeight).click().sendKeys("50",Keys.ENTER).build().perform();
    	Thread.sleep(3000);
    	
    	
    	//5) Add "Poise Executive Chair in Black Colour" chair to Wishlist
    	
    	js.executeScript("window.scrollBy(0,400)");
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-productname='Poise Executive Chair in Black Colour']"))).click();
    	Thread.sleep(3000);
    	
    	
    	
    	//6) Mouseover on Homeware and Click Pressure Cookers under Cookware
    	
    	action.moveToElement(driver.findElement(By.xpath("(//a[text()='Homeware'])[1]"))).perform();
    	action.pause(3000);
    	action.moveToElement(driver.findElement(By.xpath("//a[text()='Pressure Cookers']"))).click().build().perform();
    	Thread.sleep(3000);
    	
    	//7) Select Prestige as Brand
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Prestige']"))).click();
    	Thread.sleep(3000);
    	//8) Select Capacity as 1-3 Ltr
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='1 Ltr - 3 Ltr']"))).click();
    	Thread.sleep(3000);
    	
    	//9) Add "Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr" to Wishlist
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-productname='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr']"))).click();
    	Thread.sleep(3000);
    	//10) Verify the number of items in Wishlist
    	
    	WebElement wishList = driver.findElement(By.xpath("//div[@class='wishlist_bar']/span"));
    	System.out.println("No of Items added to wishlist : "+ wishList.getText());
    	
    	//11) Navigate to Wishlist
    	
    	driver.findElement(By.xpath("//div[@class='wishlist_bar']/a")).click();
    	Thread.sleep(3000);
    	
    	//12) Move Pressure Cooker only to Cart from Wishlist
    	
    	driver.findElement(By.xpath("(//a[@class='addtocart_icon'])[2]")).click();
    	Thread.sleep(3000);
    	
    	//13) Check for the availability for Pincode 600128
    	
    	WebElement availability = driver.findElement(By.className("srvc_pin_text"));
    	availability.click();
    	availability.clear();
    	availability.sendKeys("600062",Keys.ENTER);
    	Thread.sleep(3000);
    	
    	//14) Click Proceed to Pay Securely
    	
    	driver.findElement(By.xpath("//div[@class='minicart_footer']/a")).click();
    	Thread.sleep(3000);
    	//15 Click Place Order;
    	
    	driver.findElement(By.xpath("(//a[text()='PLACE ORDER'])[1]")).click();
    	Thread.sleep(3000);
    	//16) Capture the screenshot of the item under Order Item
    	
    	driver.findElement(By.xpath("//span[text()='ORDER SUMMARY']")).click();
    	Thread.sleep(3000);
    	
    	
    	WebElement cartImage = driver.findElement(By.xpath("//li[contains(@class,'onepge_ordersummary slick-slide slick-current slick-active')]")); 
    	
    	File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	BufferedImage  fullImg = ImageIO.read(source);
		
    	Point location = cartImage.getLocation();
    	
    	int imgWidth = cartImage.getSize().getWidth();
    	int imgheight = cartImage.getSize().getHeight();
    	
    	BufferedImage croppedImage = fullImg.getSubimage(location.getX()+40, location.getY()+45, imgWidth, imgheight);
    	ImageIO.write(croppedImage, "png", source);
    	    	
    	File dest = new File("D://TestLeaf/Maven/pepperfry/screenshot/cookerscreenshot.jpeg");
		FileUtils.copyFile(source, dest);
		
    	
    	//17) Close the browser
		Thread.sleep(3000);
    	driver.close();
    }
}
