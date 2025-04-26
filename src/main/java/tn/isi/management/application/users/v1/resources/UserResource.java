package tn.isi.management.application.users.v1.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.users.v1.mappers.UserMapper;
import tn.isi.management.application.users.v1.models.UserResponse;
import tn.isi.management.service.users.UserService;

import java.util.HashMap;

@RestController
@RequestMapping(UserResource.CONTEXT_PATH)
public class UserResource {

    public static final String CONTEXT_PATH = "/users/v1";

    @Autowired
    UserService userService;



    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/admin/by-login/{login}")
    public ResponseEntity<?> getUserByLogin(@PathVariable String login) {
        try {
            UserResponse userResponse = UserMapper.userToUserResponse(userService.getUserByLogin(login));
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/admin/by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            UserResponse userResponse = UserMapper.userToUserResponse(userService.getUserById(id));
           return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
           return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/admin/by-role/{roleId}")
    public ResponseEntity<?> getUsersByRoleId(@PathVariable Integer roleId) {
        try {
            return ResponseEntity.ok(userService.getUsersByRoleId(roleId));
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Users not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (IllegalArgumentException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Users not found");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }









}
