package com.example.staracademybackend.repository;

import com.example.staracademybackend.model.ContactInquiry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInquiryRepository extends MongoRepository<ContactInquiry, String> {
}
