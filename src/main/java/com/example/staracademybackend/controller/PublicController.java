package com.example.staracademybackend.controller;

import com.example.staracademybackend.model.ContactInquiry;
import com.example.staracademybackend.model.EventEntity;
import com.example.staracademybackend.model.MediaEntity;
import com.example.staracademybackend.model.ServiceEntity;
import com.example.staracademybackend.model.Status;
import com.example.staracademybackend.repository.EventRepository;
import com.example.staracademybackend.repository.ServiceRepository;
import com.example.staracademybackend.service.ContactService;
import com.example.staracademybackend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend to call
public class PublicController {

    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;
    private final MediaService mediaService;
    private final ContactService contactService;

    @GetMapping("/services")
    public List<ServiceEntity> getServices() {
        return serviceRepository.findByStatus(Status.ACTIVE);
    }

    @GetMapping("/events")
    public List<EventEntity> getEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/gallery")
    public List<MediaEntity> getGallery() {
        return mediaService.getAllMedia();
    }

    @GetMapping("/gallery/service/{id}")
    public List<MediaEntity> getServiceGallery(@PathVariable String id) {
        return mediaService.getMediaByAssociation(MediaEntity.AssociatedType.SERVICE, id);
    }

    @GetMapping("/gallery/event/{id}")
    public List<MediaEntity> getEventGallery(@PathVariable String id) {
        return mediaService.getMediaByAssociation(MediaEntity.AssociatedType.EVENT, id);
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactInquiry> submitInquiry(@RequestBody ContactInquiry inquiry) {
        return ResponseEntity.ok(contactService.saveInquiry(inquiry));
    }

    @GetMapping("/media/download")
    public ResponseEntity<Resource> downloadMedia(@RequestParam String url, @RequestParam String fileName) {
        try {
            URL mediaUrl = new URL(url);
            InputStream inputStream = mediaUrl.openStream();
            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
