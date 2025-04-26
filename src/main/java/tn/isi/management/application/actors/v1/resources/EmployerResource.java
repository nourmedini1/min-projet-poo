package tn.isi.management.application.actors.v1.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.actors.v1.mappers.ActorsMapper;
import tn.isi.management.application.actors.v1.models.CreateEmployerRequest;
import tn.isi.management.domain.entities.Employer;
import tn.isi.management.service.actors.EmployerService;

import java.util.HashMap;

@RestController
@RequestMapping(EmployerResource.CONTEXT_PATH)
public class EmployerResource {

    @Autowired
    private EmployerService employerService;

    @Autowired
    private ActorsMapper actorsMapper;

    public static final String CONTEXT_PATH = "/employers/v1";

    @PostMapping("/manager/create")
    public ResponseEntity<?> createEmployer(@RequestBody CreateEmployerRequest createEmployerRequest) {
        try {
            Employer employerToCreate = actorsMapper.toEmployer(createEmployerRequest);
            Employer createdEmployer = employerService.addEmployer(employerToCreate);
            if (createdEmployer == null) {
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Employer creation failed");
                return ResponseEntity.status(400).body(errorResponse);
            }
            return ResponseEntity.status(201).body(createdEmployer);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating employer: " + e.getMessage());
        }
    }

    @PostMapping("/manager/update")
    public ResponseEntity<?> updateEmployer(@RequestBody CreateEmployerRequest createEmployerRequest) {
        try {
            Employer employerToUpdate = actorsMapper.toEmployer(createEmployerRequest);
            Employer updatedEmployer = employerService.updateEmployer(employerToUpdate);
            if (updatedEmployer == null) {
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Employer update failed");
                return ResponseEntity.status(400).body(errorResponse);
            }
            return ResponseEntity.ok(updatedEmployer);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating employer: " + e.getMessage());
        }
    }

    @DeleteMapping("/manager/delete/{id}")
    public ResponseEntity<?> deleteEmployer(@PathVariable Integer id) {
        try {
            employerService.deleteEmployer(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Employer not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-id/{id}")
    public ResponseEntity<?> getEmployerById(@PathVariable Integer id) {
        try {
            Employer employer = employerService.getEmployerById(id);
            if (employer == null) {
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Employer not found");
                return ResponseEntity.status(404).body(errorResponse);
            }
            return ResponseEntity.ok(employer);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-name/{name}")
    public ResponseEntity<?> getEmployerByName(@PathVariable String name) {
        try {
            Employer employer = employerService.getEmployerByName(name);
            if (employer == null) {
                HashMap<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Employer not found");
                return ResponseEntity.status(404).body(errorResponse);
            }
            return ResponseEntity.ok(employer);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-name-contains/{name}")
    public ResponseEntity<?> getEmployersByNameContaining(@PathVariable String name) {
        try {
            return ResponseEntity.ok(employerService.getEmployersByNameContaining(name));
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Employers not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager")
    public ResponseEntity<?> getAllEmployers() {
        try {
            return ResponseEntity.ok(employerService.getAllEmployers());
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Employers not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
