package dev.rabbit.config;

import com.mongodb.ConnectionString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Deprecated
public class MongoFactoryConfiguration {
    private MongoConfiguration mongoConfiguration;

    public MongoClientFactoryBean mongo() throws Exception {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        ConnectionString connectionString = new ConnectionString(mongoConfiguration.getUrl());
        mongo.setConnectionString(connectionString);
        return mongo;
    }

}
