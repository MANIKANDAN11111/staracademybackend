package com.example.staracademybackend.service;

import com.example.staracademybackend.model.MediaEntity;
import com.example.staracademybackend.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {

    private final S3Service s3Service;
    private final MediaRepository mediaRepository;

    public MediaEntity uploadMedia(MultipartFile file, String title, MediaEntity.AssociatedType associatedType, String associatedId) throws IOException {
        String folder = associatedType.toString().toLowerCase() + "s";
        String s3Key = s3Service.uploadFile(file, folder);
        String s3Url = s3Service.getFileUrl(s3Key);

        MediaEntity media = MediaEntity.builder()
                .title(title)
                .s3Key(s3Key)
                .s3Url(s3Url)
                .type(file.getContentType().startsWith("video") ? MediaEntity.MediaType.VIDEO : MediaEntity.MediaType.IMAGE)
                .associatedType(associatedType)
                .associatedId(associatedId)
                .build();

        return mediaRepository.save(media);
    }

    public List<MediaEntity> getMediaByAssociation(MediaEntity.AssociatedType type, String id) {
        List<MediaEntity> media = mediaRepository.findByAssociatedTypeAndAssociatedId(type, id);
        media.forEach(this::normalizeUrl);
        return media;
    }

    public List<MediaEntity> getAllMedia() {
        List<MediaEntity> media = mediaRepository.findAll();
        media.forEach(this::normalizeUrl);
        return media;
    }

    private void normalizeUrl(MediaEntity media) {
        if (media.getS3Url() != null && media.getS3Url().contains("s3.amazonaws.com")) {
            // Force regional endpoint for us-east-1 buckets to avoid PermanentRedirect
            String regionalUrl = media.getS3Url().replace("s3.amazonaws.com", "s3.us-east-1.amazonaws.com");
            media.setS3Url(regionalUrl);
        }
    }

    public void deleteMedia(String id) {
        mediaRepository.findById(id).ifPresent(media -> {
            try {
                if (media.getS3Key() != null) {
                    s3Service.deleteFile(media.getS3Key());
                }
            } catch (Exception e) {
                log.error("Failed to delete S3 file for media {}: {}", id, e.getMessage());
                // Rethrow to maintain the 403 transparency (AWS vs Spring)
                throw e; 
            }
            mediaRepository.deleteById(id);
            log.info("Successfully removed media record from DB: {}", id);
        });
    }
}
