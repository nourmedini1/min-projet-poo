package tn.isi.management.application.items.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.items.v1.mappers.ItemsMapper;
import tn.isi.management.application.items.v1.models.RoleRequest;
import tn.isi.management.domain.entities.Role;
import tn.isi.management.service.items.RoleService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(RoleResource.CONTEXT_PATH)
public class RoleResource {

    private static final Logger logger = LoggerFactory.getLogger(RoleResource.class);
    
    public static final String CONTEXT_PATH = "/roles/v1";
    
    @Autowired
    private RoleService roleService;
    
    @GetMapping("/manager")
    public ResponseEntity<?> getAllRoles() {
        try {
            logger.info("Getting all roles");
            List<Role> roles = roleService.getAllRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error retrieving all roles: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Integer id) {
        try {
            logger.info("Getting role by ID: {}", id);
            Role role = roleService.getRoleById(id);
            return ResponseEntity.ok(role);
        } catch (RuntimeException e) {
            logger.warn("Role not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving role: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/admin/by-name/{name}")
    public ResponseEntity<?> getRoleByName(@PathVariable String name) {
        try {
            logger.info("Getting role by name: {}", name);
            Role role = roleService.getRoleByName(name);
            return ResponseEntity.ok(role);
        } catch (RuntimeException e) {
            logger.warn("Role not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving role: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @PostMapping("/admin/create")
    public ResponseEntity<?> createRole(@RequestBody RoleRequest roleRequest) {
        try {
            logger.info("Creating role with name: {}", roleRequest.getName());
            Role roleToCreate = ItemsMapper.toRole(roleRequest);
            Role createdRole = roleService.addRole(roleToCreate);
            return ResponseEntity.status(201).body(createdRole);
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to create role: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Error creating role: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        try {
            logger.info("Deleting role with ID: {}", id);
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting role: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
