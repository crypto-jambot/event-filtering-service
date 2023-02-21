package dev.rabbit.config;

import com.mongodb.ConnectionString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MongoFactoryConfiguration {
    private MongoConfiguration mongoConfiguration;

    @Bean
    public MongoClientFactoryBean mongo() throws Exception {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        ConnectionString connectionString = new ConnectionString(mongoConfiguration.getUrl());
        mongo.setConnectionString(connectionString);
        return mongo;
    }

}
