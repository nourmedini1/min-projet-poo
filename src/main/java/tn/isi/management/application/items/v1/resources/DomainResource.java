package tn.isi.management.application.items.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.items.v1.mappers.ItemsMapper;
import tn.isi.management.application.items.v1.models.DomainRequest;
import tn.isi.management.domain.entities.Domain;
import tn.isi.management.service.items.DomainService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(DomainResource.CONTEXT_PATH)
public class DomainResource {

    private static final Logger logger = LoggerFactory.getLogger(DomainResource.class);
    
    public static final String CONTEXT_PATH = "/domains/v1";
    
    @Autowired
    private DomainService domainService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> createDomain(@RequestBody DomainRequest domainRequest) {
        try {
            logger.info("Creating domain with name: {}", domainRequest.getLabel());
            Domain domainToCreate = ItemsMapper.ToDomain(domainRequest);
            Domain createdDomain = domainService.addDomain(domainToCreate);
            return ResponseEntity.status(201).body(createdDomain);
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to create domain: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Error creating domain: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteDomain(@PathVariable Integer id) {
        try {
            logger.info("Deleting domain with ID: {}", id);
            domainService.deleteDomain(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting domain: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/{id}")
    public ResponseEntity<?> getDomainById(@PathVariable Integer id) {
        try {
            logger.info("Getting domain by ID: {}", id);
            Domain domain = domainService.getDomainById(id);
            return ResponseEntity.ok(domain);
        } catch (RuntimeException e) {
            logger.warn("Domain not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving domain: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager")
    public ResponseEntity<?> getAllDomains() {
        try {
            logger.info("Getting all domains");
            List<Domain> domains = domainService.getAllDomains();
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            logger.error("Error retrieving all domains: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/manager/by-label/{label}")
    public ResponseEntity<?> getDomainByLabel(@PathVariable String label) {
        try {
            logger.info("Getting domain by label: {}", label);
            Domain domain = domainService.getDomainByLabel(label);
            return ResponseEntity.ok(domain);
        } catch (RuntimeException e) {
            logger.warn("Domain not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving domain: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }



}
