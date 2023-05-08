package org.achumakin.page;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Paths;

public class PageManager {

    public Pair<Playwright, Page> getInstance(boolean headless) {
        var playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        var contextOptions = new Browser.NewContextOptions().setRecordVideoDir(Paths.get("target/videos/"));
        var context = browser.newContext(contextOptions);
        var pageInstance = context.newPage();
        return Pair.of(playwright, pageInstance);
    }

}
