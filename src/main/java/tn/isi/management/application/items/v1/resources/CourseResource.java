package tn.isi.management.application.items.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.items.v1.mappers.ItemsMapper;
import tn.isi.management.application.items.v1.models.CourseRequest;
import tn.isi.management.domain.entities.Course;
import tn.isi.management.service.items.CourseService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(CourseResource.CONTEXT_PATH)
public class CourseResource {

    private static final Logger logger = LoggerFactory.getLogger(CourseResource.class);
    
    public static final String CONTEXT_PATH = "/courses/v1";

    @Autowired
    private ItemsMapper itemsMapper;
    
    @Autowired
    private CourseService courseService;

    @PostMapping("/manager/create")
    public ResponseEntity<?> createCourse(@RequestBody CourseRequest courseRequest) {
        try {
            logger.info("Creating course with title: {}", courseRequest.getTitle());
            Course courseToCreate = itemsMapper.toCourse(courseRequest);
            Course createdCourse = courseService.addCourse(courseToCreate);
            return ResponseEntity.status(201).body(createdCourse);
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to create course: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Error creating course: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    

    
    @DeleteMapping("/manager/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        try {
            logger.info("Deleting course with ID: {}", id);
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting course: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {
        try {
            logger.info("Getting course by ID: {}", id);
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            logger.warn("Course not found: {}", e.getMessage());
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving course: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager")
    public ResponseEntity<?> getAllCourses() {
        try {
            logger.info("Getting all courses");
            List<Course> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            logger.error("Error retrieving all courses: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/manager/search")
    public ResponseEntity<?> searchCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String titleContains,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) Integer domainId,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget) {
        
        try {
            logger.info("Searching courses with filters");
            List<Course> courses = courseService.searchCourses(
                    title, titleContains, minYear, maxYear, domainId,
                    minDuration, maxDuration, minBudget, maxBudget);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            logger.error("Error searching courses: {}", e.getMessage(), e);
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
