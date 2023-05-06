package org.achumakin.model.tc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildType {

    private String id;
    private String projectId;

}
