package dev.rabbit.repositories;

import dev.rabbit.pojo.Trees;
import org.springframework.data.mongodb.repository.MongoRepository;

@Deprecated
public interface TreesRepository extends MongoRepository<Trees, String> {
}
