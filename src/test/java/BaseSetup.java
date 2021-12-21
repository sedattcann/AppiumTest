import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class BaseSetup {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    @BeforeMethod
    public void setup () throws MalformedURLException {

        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("deviceName", "Nexus 5");
            caps.setCapability("udid", "emulator-5554");
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", "9.0");
            caps.setCapability("skipUnlock","false");
            caps.setCapability("appPackage", "com.gittigidiyormobil");
            caps.setCapability("appActivity","com.gittigidiyormobil.view.startup.SplashScreen");
            caps.setCapability("noReset","true");
            driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),caps);
            wait = new WebDriverWait(driver, 10);
        } catch (Exception e)
        {
            System.out.println("driver başlatılamadı"+"\n");
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void GG() throws InterruptedException{

        WebElement check = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.gittigidiyormobil:id/appbar_search_text_autoCompleteTextView")));
        check.click();
        Actions action=new Actions(driver);
        WebElement key = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.gittigidiyormobil:id/appbar_search_text_autoCompleteTextView")));
        action.sendKeys("Bilgisayar").sendKeys(Keys.ENTER).build().perform();
        List<MobileElement> products= wait.until((ExpectedCondition<List<MobileElement>>) driver -> driver.findElements(By.className("androidx.recyclerview.widget.RecyclerView")));
        Random random =new Random();
        if (products.size()>0)
        {
            int randomProducts=random.nextInt(products.size());
            products.get(randomProducts).click();
            System.out.println("Element Sayısı : "+products.size());
            try {
                FileWriter fileWriter=new FileWriter("C:\\Users\\SedatCan\\IdeaProjects\\AppiumTest\\src\\test\\java\\info.txt",false);
                fileWriter.write(wait.until((ExpectedCondition<WebElement>) driver -> driver.findElement(By.id("com.gittigidiyormobil:id/tv_product_title"))).getText());
                fileWriter.write("\n");
                fileWriter.write("------------------------------------------------------");
                fileWriter.write("\n");
                fileWriter.write(wait.until((ExpectedCondition<WebElement>) driver -> driver.findElement(By.id("com.gittigidiyormobil:id/tv_best_deal_price"))).getText());
                fileWriter.close();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }

        }else
        {
            System.out.println("ürünleri çekemedi");
        }
        Thread.sleep(2000);

       // String firstPrice=wait.until((ExpectedCondition<WebElement>) driver -> driver.findElement(By.id("com.gittigidiyormobil:id/tv_best_deal_price"))).getText();

//        TouchActions taction = new TouchActions(driver);
//        taction.scroll(10, 100);
//        taction.perform();
        //wait.until(ExpectedConditions.elementToBeClickable(By.id("com.gittigidiyormobil:id/btnAddBasket")));
        //wait.until(ExpectedConditions.elementToBeClickable(By.id("com.gittigidiyormobil:id/notificationCountText")));
        //driver.findElement(By.id("com.gittigidiyormobil:id/btnAddBasket")).click();
        //driver.findElement(By.id("com.gittigidiyormobil:id/notificationCountText")).click();
      //System.out.println("ilk fiyat : "+firstPrice);


        Thread.sleep(5000);
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
