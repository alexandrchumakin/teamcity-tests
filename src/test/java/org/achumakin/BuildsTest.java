package org.achumakin;

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
    void triggerNewBuild() {
        var triggerBuildResp = apiClient.triggerBuild();
        var getBuildResp = apiClient.getBuildWithState(triggerBuildResp.getId(), "finished");
        assertThat(getBuildResp.getStatus()).isEqualTo("SUCCESS");
    }

}
