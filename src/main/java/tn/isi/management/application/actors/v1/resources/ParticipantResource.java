package tn.isi.management.application.actors.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.actors.v1.mappers.ActorsMapper;
import tn.isi.management.application.actors.v1.models.CreateParticipantRequest;
import tn.isi.management.domain.entities.Participant;
import tn.isi.management.service.actors.ParticipantService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(ParticipantResource.CONTEXT_PATH)
public class ParticipantResource {

    private static final Logger logger = LoggerFactory.getLogger(ParticipantResource.class);
    
    @Autowired
    private ParticipantService participantService;

    public static final String CONTEXT_PATH = "/participants/v1";

    @PostMapping("/manager/create")
    public ResponseEntity<?> createParticipant(@RequestBody CreateParticipantRequest createParticipantRequest) {
        try {
            logger.info("Creating participant with email: {}", createParticipantRequest.getEmail());
            Participant participantToCreate = ActorsMapper.toParticipant(createParticipantRequest);
            Participant createdParticipant = participantService.addParticipant(participantToCreate);
            
            if (createdParticipant == null) {
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Participant creation failed");
                return ResponseEntity.status(400).body(errorResponse);
            }
            
            logger.info("Participant created successfully: {}", createdParticipant.getId());
            return ResponseEntity.status(201).body(createdParticipant);
        } catch (Exception e) {
            logger.error("Error creating participant: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating participant: " + e.getMessage());
        }
    }


    @DeleteMapping("/manager/delete/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable Integer id) {
        try {
            logger.info("Deleting participant with ID: {}", id);
            participantService.deleteParticipant(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Participant not found with ID: {}", id);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Participant not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error deleting participant with ID {}: {}", id, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-id/{id}")
    public ResponseEntity<?> getParticipantById(@PathVariable Integer id) {
        try {
            logger.info("Getting participant by ID: {}", id);
            Participant participant = participantService.getParticipantById(id);
            
            if (participant == null) {
                logger.warn("Participant not found with ID: {}", id);
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Participant not found");
                return ResponseEntity.status(404).body(errorResponse);
            }
            
            return ResponseEntity.ok(participant);
        } catch (Exception e) {
            logger.error("Error getting participant by ID {}: {}", id, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-email/{email}")
    public ResponseEntity<?> getParticipantByEmail(@PathVariable String email) {
        try {
            logger.info("Getting participant by email: {}", email);
            Participant participant = participantService.getParticipantByEmail(email);

            if (participant == null) {
                logger.warn("Participant not found with email: {}", email);
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Participant not found");
                return ResponseEntity.status(404).body(errorResponse);
            }

            return ResponseEntity.ok(participant);
        } catch (Exception e) {
            logger.error("Error getting participant by email {}: {}", email, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-phone/{phone}")
    public ResponseEntity<?> getParticipantByPhone(@PathVariable String phone) {
        try {
            logger.info("Getting participant by phone: {}", phone);
            Participant participant = participantService.getParticipantByPhone(phone);

            if (participant == null) {
                logger.warn("Participant not found with phone: {}", phone);
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Participant not found");
                return ResponseEntity.status(404).body(errorResponse);
            }

            return ResponseEntity.ok(participant);
        } catch (Exception e) {
            logger.error("Error getting participant by phone {}: {}", phone, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-structure/{idStructure}")
    public ResponseEntity<?> getParticipantsByStructure(@PathVariable Integer idStructure) {
        try {
            logger.info("Getting participants by structure ID: {}", idStructure);
            List<Participant> participants = participantService.getParticipantsByStructureId(idStructure);

            if (participants == null || participants.isEmpty()) {
                logger.warn("No participants found for structure ID: {}", idStructure);
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No participants found");
                return ResponseEntity.status(404).body(errorResponse);
            }

            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            logger.error("Error getting participants by structure ID {}: {}", idStructure, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-course/{idCourse}")
    public ResponseEntity<?> getParticipantsByCourse(@PathVariable Integer idCourse) {
        try {
            logger.info("Getting participants by course ID: {}", idCourse);
            List<Participant> participants = participantService.getParticipantsByCourseId(idCourse);

            if (participants == null || participants.isEmpty()) {
                logger.warn("No participants found for course ID: {}", idCourse);
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No participants found");
                return ResponseEntity.status(404).body(errorResponse);
            }

            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            logger.error("Error getting participants by course ID {}: {}", idCourse, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-profile/{idProfile}")
    public ResponseEntity<?> getParticipantsByProfile(@PathVariable Integer idProfile) {
        try {
            logger.info("Getting participants by profile ID: {}", idProfile);
            List<Participant> participants = participantService.getParticipantsByProfileId(idProfile);

            if (participants == null || participants.isEmpty()) {
                logger.warn("No participants found for profile ID: {}", idProfile);
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No participants found");
                return ResponseEntity.status(404).body(errorResponse);
            }

            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            logger.error("Error getting participants by profile ID {}: {}", idProfile, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }



    @GetMapping("/manager")
    public ResponseEntity<?> getAllParticipants() {
        try {
            logger.info("Getting all participants");
            List<Participant> participants = participantService.getAllParticipants();
            return ResponseEntity.ok(participants);
        } catch (IllegalArgumentException e) {
            logger.warn("Participants not found");
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Participants not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error getting all participants: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
