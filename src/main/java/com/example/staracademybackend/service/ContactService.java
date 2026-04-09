package com.example.staracademybackend.service;

import com.example.staracademybackend.model.ContactInquiry;
import com.example.staracademybackend.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactInquiryRepository repository;

    public ContactInquiry saveInquiry(ContactInquiry inquiry) {
        inquiry.setCreatedAt(LocalDateTime.now());
        return repository.save(inquiry);
    }

    public List<ContactInquiry> getAllInquiries() {
        return repository.findAll();
    }
}
