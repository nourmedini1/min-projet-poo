package tn.isi.management.application.items.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.items.v1.mappers.ItemsMapper;
import tn.isi.management.application.items.v1.models.StructureRequest;
import tn.isi.management.domain.entities.Structure;
import tn.isi.management.service.items.StructureService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(StructureResource.CONTEXT_PATH)
public class StructureResource {

    private static final Logger logger = LoggerFactory.getLogger(StructureResource.class);
    
    public static final String CONTEXT_PATH = "/structures/v1";
    
    @Autowired
    private StructureService structureService;

    @Autowired
    private ItemsMapper itemsMapper;

    @PostMapping("/admin/create")
    public ResponseEntity<?> createStructure(@RequestBody StructureRequest structureRequest) {
        try {
            logger.info("Creating structure with name: {}", structureRequest.getLabel());
            Structure structureToCreate = itemsMapper.toStructure(structureRequest);
            Structure createdStructure = structureService.addStructure(structureToCreate);
            return ResponseEntity.status(201).body(createdStructure);
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to create structure: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Error creating structure: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteStructure(@PathVariable Integer id) {
        try {
            logger.info("Deleting structure with ID: {}", id);
            structureService.deleteStructure(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting structure: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/{id}")
    public ResponseEntity<?> getStructureById(@PathVariable Integer id) {
        try {
            logger.info("Getting structure by ID: {}", id);
            Structure structure = structureService.getStructureById(id);
            return ResponseEntity.ok(structure);
        } catch (RuntimeException e) {
            logger.warn("Structure not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving structure: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager")
    public ResponseEntity<?> getAllStructures() {
        try {
            logger.info("Getting all structures");
            List<Structure> structures = structureService.getAllStructures();
            return ResponseEntity.ok(structures);
        } catch (Exception e) {
            logger.error("Error retrieving all structures: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/by-label/{label}")
    public ResponseEntity<?> getStructureByLabel(@PathVariable String label) {
        try {
            logger.info("Getting structure by label: {}", label);
            Structure structure = structureService.getStructureByLabel(label);
            return ResponseEntity.ok(structure);
        } catch (RuntimeException e) {
            logger.warn("Structure not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving structure: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
