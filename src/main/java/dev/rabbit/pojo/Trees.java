package dev.rabbit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "trees")
@Data
@AllArgsConstructor
public class Trees {
    @Id
    @Field("_id")
    private String id;
    @Field("trees")
    private List<Tree> trees;

    @Data
    @AllArgsConstructor
    public static class Tree {
        @Field("node")
        private Node node;
        @Field("swap")
        private Swap swap;
        @Field("children")
        private List<Tree> children;
    }

    @Data
    @AllArgsConstructor
    public static class Node {
        @Field("name")
        private String name;
        @Field("symbol")
        private String symbol;
        @Field("address")
        private String address;
        @Field("decimals")
        private Integer decimals;
    }

    @Data
    @AllArgsConstructor
    public static class Swap {
        @Field("exchanges")
        private List<Exchange> exchanges;
        @Field("ordered")
        private Boolean ordered;
    }

    @Data
    @AllArgsConstructor
    public static class Exchange {
        @Field("pool")
        private String pool;
        @Field("fee")
        private Integer fee;
        @Field("name")
        private String name;
    }


}
