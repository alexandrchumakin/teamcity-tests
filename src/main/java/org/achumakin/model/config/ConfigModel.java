package org.achumakin.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModel {

    private Boolean headless;
    private BaseModel portal;
    private BaseModel local;
    private ApiModel api;

}
