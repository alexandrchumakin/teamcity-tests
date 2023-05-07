package org.achumakin.page;

import com.microsoft.playwright.Locator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPage extends BasePage {

    private final Locator loginPasswordSwitch;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage() {
        loginPasswordSwitch = pageInstance.locator("#loginPasswordSwitch");
        usernameInput = pageInstance.locator("#username");
        passwordInput = pageInstance.locator("#password");
        loginButton = pageInstance.locator("[name=submitLogin]");
    }

    public void loginWithUser() {
        log.info("Logging into TeamCity with {} user", portalConfig.getUsername());
        loginPasswordSwitch.click();
        usernameInput.type(portalConfig.getUsername());
        passwordInput.type(portalConfig.getPassword());
        loginButton.click();
    }

}
