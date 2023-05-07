package org.achumakin.infra;

import org.achumakin.page.AgentsPage;
import org.achumakin.page.SetupAdminPage;
import org.achumakin.page.StartPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("TeamCity installation tests suite")
@EnabledIfEnvironmentVariable(named = "INFRA_TESTS", matches = "true")
public class InstallationTest {

    private static StartPage startPage;

    @BeforeAll
    static void setUp() {
        startPage = new StartPage();
    }

    @AfterAll
    static void cleanUp() {
        startPage.closeBrowser();
    }

    @Test
    @DisplayName("Configure Agent with new TeamCity instance")
    void configureAgent() {
        startPage.configureDefaultInstance();
        new SetupAdminPage().createAdminUser();
        new AgentsPage().authorizeAgent();
        assertThat(startPage.getAgentsCount()).hasText("1");
    }

}
