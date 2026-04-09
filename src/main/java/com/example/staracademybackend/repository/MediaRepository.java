package com.example.staracademybackend.repository;

import com.example.staracademybackend.model.MediaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends MongoRepository<MediaEntity, String> {
    List<MediaEntity> findByAssociatedTypeAndAssociatedId(MediaEntity.AssociatedType associatedType, String associatedId);
}
