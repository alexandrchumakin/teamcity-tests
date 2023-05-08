package org.achumakin.extension;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.nio.file.Paths;

public class JUnitExtension implements AfterTestExecutionCallback {

    private static Page page;

    public static void setPage(Page page) {
        JUnitExtension.page = page;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        var isFailed = context.getExecutionException().isPresent();
        if (isFailed) {
            var screenPath = String.format("target/screenshots/%s.png", context.getDisplayName());
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenPath)));
        }
    }
}
