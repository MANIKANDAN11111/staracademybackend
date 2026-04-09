package com.example.staracademybackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "media")
public class MediaEntity {
    @Id
    private String id;
    private String title;
    private String s3Key;
    private String s3Url;
    private MediaType type;
    private AssociatedType associatedType;
    private String associatedId;

    public enum MediaType {
        IMAGE, VIDEO
    }

    public enum AssociatedType {
        SERVICE, EVENT
    }
}
