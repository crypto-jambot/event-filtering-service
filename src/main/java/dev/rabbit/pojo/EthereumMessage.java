package dev.rabbit.pojo;

import org.springframework.data.web.JsonPath;

public interface EthereumMessage {
    @JsonPath({"$.id"})
    String getId();

    @JsonPath({"$.ordered"})
    Boolean getOrdered();


}
