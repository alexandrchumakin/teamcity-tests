package org.achumakin;

import org.achumakin.page.LoginPage;
import org.achumakin.page.ProjectsPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Projects tests suite")
public class ProjectsTest {

    private static ProjectsPage projectsPage;

    @BeforeAll
    static void setUp() {
        new LoginPage().loginWithUser();
        projectsPage = new ProjectsPage();
    }

    @AfterAll
    static void cleanUp() {
        projectsPage.deleteProject();
    }

    @Test
    @DisplayName("Add new project with existing Kotlin script configuration")
    void addNewProject() {
        projectsPage.createProjectFromRepo();
        assertThat(projectsPage.getSuccessMessage()).containsText("project settings have been successfully loaded");
        assertThat(projectsPage.getConfigurationsTable()).containsText("Build");
    }

}
