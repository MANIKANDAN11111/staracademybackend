package com.example.staracademybackend.repository;

import com.example.staracademybackend.model.ServiceEntity;
import com.example.staracademybackend.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends MongoRepository<ServiceEntity, String> {
    List<ServiceEntity> findByStatus(Status status);
}
