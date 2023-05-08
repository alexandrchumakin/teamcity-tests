package org.achumakin.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.achumakin.core.ConfigReader;
import org.achumakin.model.config.BaseModel;

public class BasePage {

    public static Page pageInstance;
    protected static Playwright playwright;
    protected static BaseModel portalConfig;

    public BasePage() {
        if (pageInstance == null) {
            var config = ConfigReader.getConfig();
            var instance = new PageManager().getInstance(config.getHeadless());
            playwright = instance.getLeft();
            pageInstance = instance.getRight();
            portalConfig = config.getPortal();
            pageInstance.navigate(portalConfig.getBaseUrl());
        }
    }

    public void closeBrowser() {
        playwright.close();
    }

}
