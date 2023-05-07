package org.achumakin.page;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.achumakin.core.ConfigReader;
import org.achumakin.model.config.BaseModel;

public class BasePage {

    protected static Page pageInstance;
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static BaseModel portalConfig;

    public BasePage() {
        if (pageInstance == null) {
            var config = ConfigReader.getConfig();
            portalConfig = config.getPortal();
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(config.getHeadless()));
            context = browser.newContext();
            pageInstance = context.newPage();
            pageInstance.navigate(portalConfig.getBaseUrl());
        }
    }

    public void closeBrowser() {
        playwright.close();
    }

}
