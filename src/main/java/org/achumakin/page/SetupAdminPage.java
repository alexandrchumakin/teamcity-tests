package org.achumakin.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import lombok.extern.slf4j.Slf4j;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class SetupAdminPage extends StartPage {

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator confirmPasswordInput;
    private final Locator createAccountButton;

    public SetupAdminPage() {
        usernameInput = pageInstance.locator("#input_teamcityUsername");
        passwordInput = pageInstance.locator("#password1");
        confirmPasswordInput = pageInstance.locator("#retypedPassword");
        createAccountButton = pageInstance.locator("[value='Create Account']");
    }

    public void createAdminUser() {
        log.info("Creating admin user");
        usernameInput.type(localConfig.getUsername());
        passwordInput.type(localConfig.getPassword());
        confirmPasswordInput.type(localConfig.getPassword());
        createAccountButton.click();
        assertThat(createAccountButton).isHidden(new LocatorAssertions.IsHiddenOptions().setTimeout(40000));
    }

}
