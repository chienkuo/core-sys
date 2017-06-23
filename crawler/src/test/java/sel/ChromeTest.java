package sel;

/**
 * Created by Akuo on 2017/4/12.
 */

import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@RunWith(JUnit4.class)
public class ChromeTest extends TestCase {
    private static final Logger logger = LoggerFactory.getLogger(ChromeTest.class);
    private static ChromeDriverService service;
    private WebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:\\Users\\pc\\Downloads\\chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterClass
    public static void createAndStopService() {
        service.stop();
    }

    @Before
    public void createDriver() {
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testGoogleSearch() {
        driver.get("https://zhidao.baidu.com/question/1993438489854455427.html");
        WebElement bestDiv = driver.findElement(By.className("wgt-best"));
        String id = bestDiv.getAttribute("id");
        if (id != null && id.startsWith("best-answer-")) {
            WebElement content = driver.findElement(By.id("best-content-" + id.replace("best-answer-", "")));
            logger.info(content.getText());
        }
        WebElement title = driver.findElement(By.className("ask-title"));
        assertEquals("谢娜回应打压吴昕是怎么回事？", title.getText());
    }
}
