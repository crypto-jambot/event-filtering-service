package dev.rabbit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rabbit.gateway.PubSubOutboundGateway;
import dev.rabbit.pojo.EthereumMessage;
import dev.rabbit.pojo.Trees;
import dev.rabbit.repositories.TreesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Component
//@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MessagingService {
    //    private final PathRepository pathRepository;
    private final TreesRepository treesRepository;
    private final PubSubOutboundGateway pubSubOutboundGateway;
    private final ObjectMapper objectMapper;
    private int count = 1;

    @Autowired
    public MessagingService(TreesRepository treesRepository, PubSubOutboundGateway pubSubOutboundGateway,
            ObjectMapper objectMapper) {
        this.treesRepository = treesRepository;
        this.pubSubOutboundGateway = pubSubOutboundGateway;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"tasks"})
    public void listen(EthereumMessage ethereumMessage) {
        try {
            Optional<Trees> optional =
                    treesRepository.findById(ethereumMessage.getAddress() + ethereumMessage.getOrdered());
            long latency = System.currentTimeMillis() - ethereumMessage.getTimestamp();
            if (optional.isPresent()) {
                Trees trees = optional.get();
                Message<String> pubsubMessage =
                        MessageBuilder.createMessage(objectMapper.writeValueAsString(trees), new MessageHeaders(
                                Map.of("formatted-date", new Date().toString(), "message-count", String.valueOf(count),
                                        "errorChannel", "nullChannel", "latency",
                                        String.format("%dms", latency, "size", trees.getTrees().size()))));
                pubSubOutboundGateway.sendToPubsub(pubsubMessage);
                count++;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
