package com.example.staracademybackend.controller;

import com.example.staracademybackend.dto.LoginRequest;
import com.example.staracademybackend.dto.LoginResponse;
import com.example.staracademybackend.model.ContactInquiry;
import com.example.staracademybackend.model.EventEntity;
import com.example.staracademybackend.model.MediaEntity;
import com.example.staracademybackend.model.ServiceEntity;
import com.example.staracademybackend.model.AwardEntity;
import com.example.staracademybackend.repository.EventRepository;
import com.example.staracademybackend.repository.ServiceRepository;
import com.example.staracademybackend.repository.AwardRepository;
import com.example.staracademybackend.service.AuthService;
import com.example.staracademybackend.service.ContactService;
import com.example.staracademybackend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;
    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;
    private final AwardRepository awardRepository;
    private final MediaService mediaService;
    private final ContactService contactService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    // Services CRUD
    @GetMapping("/services")
    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceEntity service) {
        return ResponseEntity.ok(serviceRepository.save(service));
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable String id, @RequestBody ServiceEntity service) {
        service.setId(id);
        return ResponseEntity.ok(serviceRepository.save(service));
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Events CRUD
    @GetMapping("/events")
    public List<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    public ResponseEntity<EventEntity> createEvent(@RequestBody EventEntity event) {
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventEntity> updateEvent(@PathVariable String id, @RequestBody EventEntity event) {
        event.setId(id);
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Awards CRUD
    @GetMapping("/awards")
    public List<AwardEntity> getAllAwards() {
        return awardRepository.findAll();
    }

    @PostMapping("/awards")
    public ResponseEntity<AwardEntity> createAward(@RequestBody AwardEntity award) {
        return ResponseEntity.ok(awardRepository.save(award));
    }

    @PutMapping("/awards/{id}")
    public ResponseEntity<AwardEntity> updateAward(@PathVariable String id, @RequestBody AwardEntity award) {
        award.setId(id);
        return ResponseEntity.ok(awardRepository.save(award));
    }

    @DeleteMapping("/awards/{id}")
    public ResponseEntity<Void> deleteAward(@PathVariable String id) {
        awardRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Media Upload
    @PostMapping("/media/upload")
    public ResponseEntity<MediaEntity> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("associatedType") MediaEntity.AssociatedType associatedType,
            @RequestParam("associatedId") String associatedId) throws IOException {
        return ResponseEntity.ok(mediaService.uploadMedia(file, title, associatedType, associatedId));
    }

    @DeleteMapping("/media/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable String id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/inquiries")
    public List<ContactInquiry> getInquiries() {
        return contactService.getAllInquiries();
    }
}
