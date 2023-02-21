package dev.rabbit.repositories;

import dev.rabbit.pojo.Path;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PathRepository extends MongoRepository<Path, String> {
    @Query(value = "{$text: { $search: '?0' }}")
    List<Path> findByAddress(String address);
}
