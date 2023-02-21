package dev.rabbit.pojo;

import org.springframework.data.web.JsonPath;

public interface EthereumMessage {
    @JsonPath({"$.address"})
    String getAddress();

    @JsonPath({"$.ordered"})
    Boolean getOrdered();

    @JsonPath({"$.timestamp"})
    Long getTimestamp();

    @JsonPath({"$.id"})
    String getId();
}
