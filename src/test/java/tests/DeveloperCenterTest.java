package tests;

import setup.Setup;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DeveloperCenterTest extends Setup {
    // Locators
    private final String cloudURL = "https://developer.genesys.cloud/";
    private final String homePageTitle = "Genesys Cloud Developer Center";
    private final String apiExplorerTitle = "API Explorer";
    private final String apiExplorerPlaceholder = "Type any part of a resource path or description to filter (e.g. users/me)";
    private final String endpointDescription = "Query a list of Messaging Campaigns";
    private final String apiRequestSection = "API Request";
    private final String apiResponseSection = "API Responses";
    private final String apiSuccessResponseSchema = "200 - successful operation";
    private final String endpointParameter = "pageSize";
    private final String cookieConsentButton = "Save Settings";

    @Test(dependsOnGroups = {"api"})
    public void validateDeveloperCenter() {
        page.navigate(cloudURL);
        assertThat(page).hasTitle(homePageTitle);
        cookieConsent();
        page.locator("text=" + apiExplorerTitle).click();
        assertThat(page).hasTitle(apiExplorerTitle);
        page.getByPlaceholder(apiExplorerPlaceholder).fill(verifiedEndpoint);

        // API endpoint explorer
        String endpointSelector = verifiedEndpoint.replace("/", "-");
        String endpointRowSelector = "#get" + endpointSelector + " .path";
        assertThat(page.locator(endpointRowSelector)).isVisible();
        page.locator(endpointRowSelector).click();
        assertThat(page.getByText(endpointDescription)).isVisible();

        // API endpoint Request section
        assertThat(page.getByText(apiRequestSection)).isVisible();
        assertThat(page.getByText(endpointParameter)).isVisible();

        // API endpoint response section
        Locator responseRow = page.getByText(apiResponseSection);
        assertThat(responseRow).isVisible();
        responseRow.click();
        assertThat(page.getByText(apiSuccessResponseSchema)).isVisible();
    }

    private void cookieConsent() {
        Locator cookieBanner = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(cookieConsentButton));
        if (cookieBanner.isVisible()) {
            cookieBanner.click();
        }
    }
}
