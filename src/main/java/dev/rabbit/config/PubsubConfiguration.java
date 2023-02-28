package dev.rabbit.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pubsub")
@Data
@Slf4j
public class PubsubConfiguration {
    private String topic;

    @Bean
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public MessageHandler messageSender(PubSubTemplate pubSubTemplate) {

        PubSubMessageHandler adapter =
                new PubSubMessageHandler(pubSubTemplate, topic);
        adapter.setSuccessCallback((ackId, message) -> log.info(
                String.format("Messages sent: %s", message.getHeaders().get("message-count"))));
        adapter.setFailureCallback(
                (cause, message) -> {
                    if (!cause.equals("due to null")) {
                        log.info(
                                String.format("Error sending message %s due to %s", message.getHeaders().get("id"),
                                        cause
                                ));
                    }

                }

        );

        return adapter;
    }
    
}
