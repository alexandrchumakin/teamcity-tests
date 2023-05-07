package org.achumakin.api;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.achumakin.core.ConfigReader;
import org.achumakin.model.config.ConfigModel;
import org.achumakin.model.tc.BuildQueueRequest;
import org.achumakin.model.tc.BuildQueueResponse;
import org.achumakin.model.tc.BuildType;
import org.achumakin.model.tc.GetBuildResponse;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static io.restassured.RestAssured.given;

@Slf4j
public class TeamCityApiClient {

    private final ConfigModel config;

    public TeamCityApiClient() {
        config = ConfigReader.getConfig();
    }

    public BuildQueueResponse triggerBuild() {
        log.info("Triggering new build for project {}", config.getApi().getBuild().getProjectId());
        var body = BuildQueueRequest
                .builder()
                .buildType(BuildType
                        .builder()
                        .id(config.getApi().getBuild().getId())
                        .projectId(config.getApi().getBuild().getProjectId())
                        .build())
                .build();
        return getReqSpec()
                .body(body)
                .post("httpAuth/app/rest/buildQueue")
                .as(BuildQueueResponse.class);
    }

    public GetBuildResponse getBuild(String buildId) {
        log.info("Get info for build with id {}", buildId);
        return getReqSpec()
                .get(String.format("httpAuth/app/rest/builds/%s", buildId))
                .as(GetBuildResponse.class);
    }

    public GetBuildResponse getBuildWithState(String buildId, String state) {
        Predicate<GetBuildResponse> predicate = response -> response.getState().equals(state);
        Callable<GetBuildResponse> supplier = () -> getBuild(buildId);
        return Awaitility.await()
                .atMost(Duration.ofSeconds(60))
                .pollInterval(Duration.ofSeconds(5))
                .ignoreExceptions()
                .until(supplier, predicate);
    }

    private RequestSpecification getReqSpec() {
        return given()
                .auth()
                .basic(config.getPortal().getUsername(), config.getPortal().getPassword())
                .baseUri(config.getPortal().getBaseUrl())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

}
