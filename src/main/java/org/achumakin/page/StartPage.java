package org.achumakin.page;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.LocatorAssertions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.achumakin.core.ConfigReader;
import org.achumakin.model.config.BaseModel;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/*
 This class combines locators from multiple pages of TeamCity configuration pages.
 With a project growing it should be split to multiple pages.
 */
@Slf4j
public class StartPage {

    protected static Page pageInstance;
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static BaseModel localConfig;
    protected final Locator agentsLink;
    @Getter
    private final Locator agentsCount;
    private final Locator proceedButton;
    private final Locator dbTypeSelect;
    private final Locator initializingMessage;
    private final Locator sendStatisticsCheckbox;
    private final Locator acceptLicenseCheckbox;
    private final Locator continueButton;

    public StartPage() {
        if (pageInstance == null) {
            var config = ConfigReader.getConfig();
            localConfig = config.getLocal();
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(config.getHeadless()));
            context = browser.newContext();
            pageInstance = context.newPage();
            pageInstance.navigate(localConfig.getBaseUrl());
        }
        agentsLink = pageInstance.locator("a[title='Agents']");
        agentsCount = pageInstance.locator("div", new Page.LocatorOptions().setHas(agentsLink))
                .locator("span[data-hint-container-id='header-agents-active']");
        proceedButton = pageInstance.locator("#proceedButton");
        dbTypeSelect = pageInstance.locator("#dbType");
        initializingMessage = pageInstance.locator("text=Initializing TeamCity server components");
        sendStatisticsCheckbox = pageInstance.locator("#sendUsageStatistics");
        acceptLicenseCheckbox = pageInstance.locator("#accept");
        continueButton = pageInstance.locator("[value='Continue »']");
    }

    public void configureDefaultInstance() {
        log.info("Configuring default instance");
        proceedButton.click(new Locator.ClickOptions().setTimeout(60000));
        assertThat(dbTypeSelect).hasValue("HSQLDB2");
        proceedButton.click();
        assertThat(initializingMessage).isVisible();
        assertThat(initializingMessage).isHidden(new LocatorAssertions.IsHiddenOptions().setTimeout(120000));
        log.info("Components are initialized");
        assertThat(sendStatisticsCheckbox).isChecked();
        acceptLicenseCheckbox.click();
        continueButton.click();
    }

    public void closeBrowser() {
        playwright.close();
    }

}
