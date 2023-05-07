package org.achumakin.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgentsPage extends StartPage {

    private final Locator unauthorizedSection;
    private final Locator teamcityAgentLink;
    private final Locator authorizeButton;
    private final Locator authorizePopupButton;

    public AgentsPage() {
        unauthorizedSection = pageInstance.locator("[data-hint-container-id='unauthorized-agents']");
        teamcityAgentLink = pageInstance.locator("text=teamcity-agent-1");
        authorizeButton = pageInstance.locator(".ring-button-content",
                new Page.LocatorOptions().setHas(pageInstance.locator("text=Authorize")));
        authorizePopupButton = pageInstance.locator(".ring-island-content >> .ring-button-content >> text=Authorize");
    }

    public void authorizeAgent() {
        log.info("Authorizing TeamCity agent");
        agentsLink.click();
        unauthorizedSection.click();
        teamcityAgentLink.click();
        authorizeButton.click();
        authorizePopupButton.click();
    }

}
