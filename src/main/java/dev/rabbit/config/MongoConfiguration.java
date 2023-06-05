package dev.rabbit.config;

import dev.rabbit.utils.SecretManagerUtility;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Deprecated
public class MongoConfiguration {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final SecretManagerUtility secretManagerUtility;

    static {
        secretManagerUtility = new SecretManagerUtility();
    }

    private String user;
    @Getter(AccessLevel.NONE)
    private String password;
    @Getter(AccessLevel.NONE)
    private String projectId;
    @Getter(AccessLevel.NONE)
    private String mongoKey;
    @Getter(AccessLevel.NONE)
    private String url;

    public String getPassword() {
        return secretManagerUtility.accessSecretVersionString(projectId, mongoKey);
    }

    public String getUrl() {
        return String.format(url, getPassword());
    }
}
