package org.achumakin.cloud;

import org.achumakin.api.TeamCityApiClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Builds tests suite")
public class BuildsTest {

    private static TeamCityApiClient apiClient;

    @BeforeAll
    static void setUp() {
        apiClient = new TeamCityApiClient();
    }

    @Test
    @DisplayName("Trigger new build and wait for succeeded status")
    void triggerNewBuild() {
        var triggerBuildResp = apiClient.triggerBuild();
        var getBuildResp = apiClient.getBuildWithState(triggerBuildResp.getId(), "finished");
        assertThat(getBuildResp.getStatus()).isEqualTo("SUCCESS");
    }

}
