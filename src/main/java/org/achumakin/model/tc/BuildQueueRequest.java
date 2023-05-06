package org.achumakin.model.tc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildQueueRequest {

    private BuildType buildType;

}
