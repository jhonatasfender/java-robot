package Bot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ChromeBrowser {
    public static ChromeBrowser instance;

    public WebDriver driver;

    public ChromeBrowser() {
        start();
        instance = this;
    }

    public void waitDriver() {
        WebDriverWait webDriverWait = new WebDriverWait(instance.driver, Duration.ofSeconds(200l));

        webDriverWait.until(d -> {
            WebElement input = d.findElement(By.id("HourPoint"));
            if (input.isDisplayed()) {
                LocalDateTime dt = LocalDateTime.now();
                int hour = dt.getHour();
                int minute = dt.getMinute();
                input.sendKeys(hour + ":" + minute);

                d.findElement(By.id("Save")).click();
                List<WebElement> buttons = d.findElements(By.xpath("//button[contains(@class, 'ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only')]"));
                for (WebElement button : buttons) {
                    if (button.isDisplayed()) {
                        System.out.println(button.getText());
                        button.click();
                    }
                }
            }
            return d;
        });
    }

    public void start() {
        // System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver");
        // driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
        driver = new ChromeDriver();

        // driver.get("https://pontoeletronico.sondait.com.br/");
        driver.get("file:///home/stefano/Downloads/ponto/index.html");
    }
}
