package com.example.staracademybackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inquiries")
public class ContactInquiry {
    @Id
    private String id;
    private String parentName;
    private Integer studentAge;
    private String email;
    private String programInterest;
    private String message;
    private LocalDateTime createdAt;
}
