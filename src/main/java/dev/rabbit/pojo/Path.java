package dev.rabbit.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "paths")
@Deprecated
public class Path {
    @Field("swapPath")
    private List<SwapPath> swapPath;
    @Field("tokenPath")
    private List<String> tokenPath;

    @Data
    @AllArgsConstructor
    class SwapPath {
        @Field("exchanges")
        private List<Exchange> exchanges;

        @Field("ordered")
        private Boolean ordered;
    }

    @Data
    @AllArgsConstructor
    class Exchange {
        @Field("fee")
        private Integer fee;

        @Field("pool")
        private String pool;

    }

}


