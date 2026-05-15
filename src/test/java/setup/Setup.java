package setup;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Setup {
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;
    protected static String verifiedEndpoint;

    @BeforeClass
    public void testSetup() {
        try {
            RestAssured.baseURI = "https://api.mypurecloud.com/api/v2";
            playwright = Playwright.create();
            browser = playwright.chromium().launch();
            page = browser.newPage();
        } catch (Exception e) {
            System.out.println("Test setup failed - " + e.getMessage());
        }
    }

    @AfterClass
    public void testTeardown() {
        try {
            browser.close();
            playwright.close();
        } catch (Exception e) {
            System.out.println("Test teardown failed - " + e.getMessage());
        }
    }
}
