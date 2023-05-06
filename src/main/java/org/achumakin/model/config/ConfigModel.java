package org.achumakin.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModel {

    private PortalModel portal;
    private ApiModel api;

}
