package org.achumakin.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiModel {

    private BuildModel build;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuildModel {
        private String id;
        private String projectId;
    }

}
