package org.achumakin.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import lombok.Getter;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public class ProjectsPage extends BasePage {

    private final Locator createProjectButton;
    private final Locator repoUrlInput;
    private final Locator proceedButton;
    private final Locator savingIcon;
    private final Locator connectionSuccessfulMessage;
    private final Locator successMessage;
    private final Locator configurationsTable;
    private final Locator goToProjectButton;
    private final Locator actionsButton;
    private final Locator deleteProjectButton;
    private final Locator confirmationInput;
    private final Locator deleteButton;
    private final Locator projectRemovedMessage;

    public ProjectsPage() {
        createProjectButton = pageInstance.locator("[data-test='create-project']");
        repoUrlInput = pageInstance.locator("#url");
        proceedButton = pageInstance.locator("input[value='Proceed']");
        savingIcon = pageInstance.locator("//i[@id='saving' and @title='Please wait...']");
        connectionSuccessfulMessage = pageInstance.locator(".connectionSuccessful");
        successMessage = pageInstance.locator("#message_objectsCreated");
        configurationsTable = pageInstance.locator("#configurations");
        goToProjectButton = pageInstance.locator("text=Go to project page");
        actionsButton = pageInstance.locator("//button[contains(text(), 'Actions')]");
        deleteProjectButton = pageInstance.getByTitle("Delete project");
        confirmationInput = pageInstance.locator(".confirmDialog__content >> .hostnameConfirmation");
        deleteButton = pageInstance.locator("input[value='Delete']");
        projectRemovedMessage = pageInstance.locator("#message_projectRemoved");
    }

    public void createProjectFromRepo() {
        createProjectButton.click();
        repoUrlInput.type(portalConfig.getRepoUrl());
        proceedButton.click();
        waitForSaving();
        assertThat(connectionSuccessfulMessage).isVisible();
        proceedButton.click();
        waitForSaving();
    }

    public void deleteProject() {
        actionsButton.click();
        deleteProjectButton.click();
        confirmationInput.type(portalConfig.getHost());
        deleteButton.click();
        assertThat(projectRemovedMessage).isVisible();
    }

    private void waitForSaving() {
        assertThat(savingIcon).isVisible();
        assertThat(savingIcon).isHidden(new LocatorAssertions.IsHiddenOptions().setTimeout(40000)); // wait up to 40 sec
    }

}
