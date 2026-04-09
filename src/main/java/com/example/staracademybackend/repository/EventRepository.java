package com.example.staracademybackend.repository;

import com.example.staracademybackend.model.EventEntity;
import com.example.staracademybackend.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<EventEntity, String> {
    List<EventEntity> findByStatusIn(List<Status> statuses);
}
