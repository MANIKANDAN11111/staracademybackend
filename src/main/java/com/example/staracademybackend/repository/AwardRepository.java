package com.example.staracademybackend.repository;

import com.example.staracademybackend.model.AwardEntity;
import com.example.staracademybackend.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends MongoRepository<AwardEntity, String> {
    List<AwardEntity> findByStatus(Status status);
}
