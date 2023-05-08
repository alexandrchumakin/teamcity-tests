package org.achumakin.cloud;

import org.achumakin.extension.JUnitExtension;
import org.achumakin.page.LoginPage;
import org.achumakin.page.ProjectsPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Projects tests suite")
@ExtendWith(JUnitExtension.class)
public class ProjectsTest {

    private static ProjectsPage projectsPage;

    @BeforeAll
    static void setUp() {
        new LoginPage().loginWithUser();
        projectsPage = new ProjectsPage();
        JUnitExtension.setPage(ProjectsPage.pageInstance);
    }

    @AfterAll
    static void cleanUp() {
        projectsPage.deleteProject();
        projectsPage.closeBrowser();
    }

    @Test
    @DisplayName("Add new project with existing Kotlin script configuration")
    void addNewProject() {
        projectsPage.createProjectFromRepo();
        assertThat(projectsPage.getSuccessMessage()).containsText("project settings have been successfully loaded");
        assertThat(projectsPage.getConfigurationsTable()).containsText("Build");
    }

}
