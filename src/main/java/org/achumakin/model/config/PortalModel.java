package org.achumakin.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalModel {

    private String baseUrl;
    private String host;
    private String username;
    private String password;
    private String repoUrl;

    public String getPassword() {
        return getDecodedString(password);
    }

    private String getDecodedString(String value) {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        return new String(decodedBytes);
    }

}
