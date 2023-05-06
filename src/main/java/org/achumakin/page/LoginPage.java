package org.achumakin.page;

import com.microsoft.playwright.Locator;

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
        loginPasswordSwitch.click();
        usernameInput.type(portalConfig.getUsername());
        passwordInput.type(portalConfig.getPassword());
        loginButton.click();
    }

}
