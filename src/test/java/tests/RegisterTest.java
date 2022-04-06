package tests;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RegisterTest {

// DOM

    WebDriver driver;
    Faker faker;
    public static final String URL = "http://automationpractice.com/index.php";

    @BeforeTest
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        faker = new Faker(new Locale("us"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @AfterMethod
    public void tearDown(){
//        driver.quit();
    }

    @Test

    public void registerUser(){
        driver.findElement(By.cssSelector(".login")).click();
        driver.findElement(By.cssSelector("input[name='email_create']")).sendKeys(RandomStringUtils.randomAlphabetic(6)+"@test.org");
        driver.findElement(By.id("SubmitCreate")).click();
        driver.findElement(By.id("uniform-id_gender1")).click();
        driver.findElement(By.cssSelector("input[name='customer_firstname']")).sendKeys(faker.name().firstName());
        driver.findElement(By.cssSelector("input[name='customer_lastname']")).sendKeys(faker.name().lastName());
        driver.findElement(By.cssSelector("input[name='passwd']")).sendKeys(faker.internet().password(6,10,true, true,true));

        WebElement days =driver.findElement(By.id("days"));
        days.click();
        Select selectDay = new Select(days);
        selectDay.selectByValue("29");

        WebElement month =driver.findElement(By.id("months"));
        month.click();
        Select selectMonth = new Select(month);
        selectMonth.selectByValue("10");

        WebElement year =driver.findElement(By.id("years"));
        year.click();
        Select selectYear = new Select(year);
        selectYear.selectByValue("2007");

        driver.findElement(By.xpath("//label[@for='newsletter']")).click();

        driver.findElement(By.xpath("//input[@name='address1']")).sendKeys(faker.address().fullAddress());
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys(faker.address().city());
        driver.findElement(By.id("id_country")).click();
        driver.findElement(By.cssSelector("#id_country>option[value='21']")).click();

        driver.findElement(By.id("id_state")).click();
        driver.findElement(By.cssSelector("#id_state>option[value='5']")).click();
        driver.findElement(By.xpath("//input[@name='postcode']")).sendKeys(faker.address().zipCode());

        driver.findElement(By.id("phone_mobile")).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(By.id("submitAccount")).click();

        String expectedText = "Welcome to your account. Here you can manage all of your personal information and orders.";
        String actualText = driver.findElement(By.cssSelector("p[class='info-account']")).getText();

        Assert.assertTrue(expectedText.equals(actualText));


    }

    @Test

    public void contactUs(){
        String projectRoot = System.getProperty("user.dir");
        driver.findElement(By.xpath("//a[@title='Contact Us']")).click();
        driver.findElement(By.xpath("//div[@id='uniform-id_contact']")).click();
        driver.findElement(By.xpath("//select/option[@value='2']")).click();
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(faker.internet().emailAddress());
        driver.findElement(By.xpath("//input[@id='id_order']")).sendKeys("" + faker.number().numberBetween(1,1000));
        driver.findElement(By.xpath("//textarea[@id='message']")).sendKeys("test message");
        driver.findElement(By.id("fileUpload")).sendKeys(projectRoot + "/src/main/resources/invoice.txt");
        driver.findElement(By.xpath("//button[@id='submitMessage']")).click();

        String expectedText = "Your message has been successfully sent to our team.";
        String actualText = driver.findElement(By.cssSelector("p[class^='alert']")).getText();
        String expectedAlertColor = "rgba(85, 198, 94, 1)";
        String actualAlertColor = driver.findElement(By.cssSelector("p[class^='alert']")).getCssValue("background-color");

        System.out.println(driver.findElement(By.cssSelector("p[class^='alert']")).getText());
        System.out.println(driver.findElement(By.cssSelector("p[class^='alert']")).getCssValue("background-color"));

        Assert.assertTrue(actualText.equals(expectedText),"The actual alert texts is not as expected");
        Assert.assertTrue(actualAlertColor.equals(expectedAlertColor), "The actual alert color is not as expected");



    }



    public String randomize(int lenght){
        String [] chars = {"a", "b", "7", "Y", "o", "z", "w"};
        String result = "";
        for (int i = 0; i < lenght; i++) {
            Random random = new Random();
            int index = random.nextInt(chars.length);
            result += chars[index];
        }
        return result;
    }

//    public WebElement getRandomElement(By locator){
//        Random random = new Random();
//        List<WebElement> list = driver.findElements(locator);
//        list.remove(0);
//
//    }



} // end class
