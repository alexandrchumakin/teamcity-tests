package org.achumakin.page;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.achumakin.core.ConfigReader;
import org.achumakin.model.PortalModel;

public class BasePage {

    protected static Page pageInstance;
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static PortalModel portalConfig;

    public BasePage() {
        if (pageInstance == null) {
            portalConfig = ConfigReader.getConfig().getPortal();
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            context = browser.newContext();
            pageInstance = context.newPage();
            pageInstance.navigate(portalConfig.getBaseUrl());
        }
    }

}
