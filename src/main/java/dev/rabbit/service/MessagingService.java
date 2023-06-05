package dev.rabbit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rabbit.gateway.PubSubOutboundGateway;
import dev.rabbit.pojo.EthereumMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@Slf4j
public class MessagingService {
    private final PubSubOutboundGateway pubSubOutboundGateway;
    private final ObjectMapper objectMapper;
    private final QueueService queueService;
    private final AtomicInteger count = new AtomicInteger(0);


    @Autowired
    public MessagingService(PubSubOutboundGateway pubSubOutboundGateway,
            ObjectMapper objectMapper, QueueService queueService) {
        this.pubSubOutboundGateway = pubSubOutboundGateway;
        this.objectMapper = objectMapper;
        this.queueService = queueService;
    }

    @RabbitListener(queues = {"tasks"})
    public void listen(EthereumMessage ethereumMessage) {
        try {
            if (queueService.checkIfInQueue(ethereumMessage.getId())) return;
            count.getAndIncrement();
            Message<String> pubsubMessage = MessageBuilder.createMessage(objectMapper.writeValueAsString(
                            Map.of("id", ethereumMessage.getId(), "ordered", ethereumMessage.getOrdered())),
                    new MessageHeaders(Map.of("message-count", count.get())));
            pubSubOutboundGateway.sendToPubsub(pubsubMessage);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
