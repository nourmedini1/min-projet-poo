package tn.isi.management.application.items.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.items.v1.mappers.ItemsMapper;
import tn.isi.management.application.items.v1.models.ProfileRequest;
import tn.isi.management.domain.entities.Profile;
import tn.isi.management.service.items.ProfileService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(ProfileResource.CONTEXT_PATH)
public class ProfileResource {

    private static final Logger logger = LoggerFactory.getLogger(ProfileResource.class);
    
    public static final String CONTEXT_PATH = "/profiles/v1";
    
    @Autowired
    private ProfileService profileService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> createProfile(@RequestBody ProfileRequest profileRequest) {
        try {
            logger.info("Creating profile with name: {}", profileRequest.getLabel());
            Profile profileToCreate = ItemsMapper.toProfile(profileRequest);
            Profile createdProfile = profileService.addProfile(profileToCreate);
            return ResponseEntity.status(201).body(createdProfile);
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to create profile: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Error creating profile: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Integer id) {
        try {
            logger.info("Deleting profile with ID: {}", id);
            profileService.deleteProfile(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting profile: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Integer id) {
        try {
            logger.info("Getting profile by ID: {}", id);
            Profile profile = profileService.getProfileById(id);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            logger.warn("Profile not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving profile: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager")
    public ResponseEntity<?> getAllProfiles() {
        try {
            logger.info("Getting all profiles");
            List<Profile> profiles = profileService.getAllProfiles();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            logger.error("Error retrieving all profiles: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/by-label/{label}")
    public ResponseEntity<?> getProfileByLabel(@PathVariable String label) {
        try {
            logger.info("Getting profile by label: {}", label);
            Profile profile = profileService.getProfileByLabel(label);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            logger.warn("Profile not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving profile: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
