package tn.isi.management.application.actors.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.actors.v1.mappers.ActorsMapper;
import tn.isi.management.application.actors.v1.models.CreateInstructorRequest;
import tn.isi.management.application.actors.v1.models.UpdateInstructorRequest;
import tn.isi.management.domain.entities.Instructor;
import tn.isi.management.service.actors.InstructorService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(InstructorResource.CONTEXT_PATH)
public class InstructorResource {

    private static final Logger logger = LoggerFactory.getLogger(InstructorResource.class);
    
    @Autowired
    private InstructorService instructorService;

    @Autowired
    private ActorsMapper actorsMapper;

    public static final String CONTEXT_PATH = "/instructors/v1";

    @PostMapping("/manager/create")
    public ResponseEntity<?> createInstructor(@RequestBody CreateInstructorRequest createInstructorRequest) {
        try {
            logger.info("Creating instructor with email: {}", createInstructorRequest.getEmail());
            Instructor instructorToCreate = actorsMapper.toInstructor(createInstructorRequest);
            Instructor createdInstructor = instructorService.addInstructor(instructorToCreate);
            
            if (createdInstructor == null) {
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Instructor creation failed");
                return ResponseEntity.status(400).body(errorResponse);
            }
            
            logger.info("Instructor created successfully: {}", createdInstructor.getId());
            return ResponseEntity.status(201).body(createdInstructor);
        } catch (Exception e) {
            logger.error("Error creating instructor: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating instructor: " + e.getMessage());
        }
    }

    @PostMapping("/manager/update")
    public ResponseEntity<?> updateInstructor(@RequestBody UpdateInstructorRequest updateInstructorRequest) {
        try {
            Instructor instructorToUpdate = actorsMapper.updateInstructorRequestToInstructor(updateInstructorRequest);
            Instructor updatedInstructor = instructorService.updateInstructor(instructorToUpdate);
            
            if (updatedInstructor == null) {
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Instructor update failed");
                return ResponseEntity.status(400).body(errorResponse);
            }
            
            logger.info("Instructor updated successfully: {}", updatedInstructor.getId());
            return ResponseEntity.ok(updatedInstructor);
        } catch (Exception e) {
            logger.error("Error updating instructor: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error updating instructor: " + e.getMessage());
        }
    }

    @DeleteMapping("/manager/delete/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Integer id) {
        try {
            logger.info("Deleting instructor with ID: {}", id);
            instructorService.deleteInstructor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Instructor not found with ID: {}", id);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Instructor not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error deleting instructor with ID {}: {}", id, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-id/{id}")
    public ResponseEntity<?> getInstructorById(@PathVariable Integer id) {
        try {
            logger.info("Getting instructor by ID: {}", id);
            Instructor instructor = instructorService.getInstructorById(id);
            
            if (instructor == null) {
                logger.warn("Instructor not found with ID: {}", id);
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Instructor not found");
                return ResponseEntity.status(404).body(errorResponse);
            }
            
            return ResponseEntity.ok(instructor);
        } catch (Exception e) {
            logger.error("Error getting instructor by ID {}: {}", id, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }



    @GetMapping("/manager")
    public ResponseEntity<?> getAllInstructors() {
        try {
            logger.info("Getting all instructors");
            List<Instructor> instructors = instructorService.getAllInstructors();
            return ResponseEntity.ok(instructors);
        } catch (IllegalArgumentException e) {
            logger.warn("Instructors not found");
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Instructors not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error getting all instructors: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/by-email/{email}")
    public ResponseEntity<?> getInstructorByEmail(@PathVariable String email) {
        try {
            logger.info("Getting instructors by email: {}", email);
            Instructor instructor = instructorService.getInstructorByEmail(email);
            return ResponseEntity.ok(instructor);
        } catch (IllegalArgumentException e) {
            logger.warn("Instructors not found with email: {}", email);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Instructors not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error getting instructors by email {}: {}", email, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-phone/{phone}")
    public ResponseEntity<?> getInstructorsByPhone(@PathVariable String phone) {
        try {
            logger.info("Getting instructors by phone: {}", phone);
            Instructor instructor = instructorService.getInstructorByPhone(phone);
            return ResponseEntity.ok(instructor);
        } catch (IllegalArgumentException e) {
            logger.warn("Instructors not found with phone: {}", phone);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Instructors not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error getting instructors by phone {}: {}", phone, e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
