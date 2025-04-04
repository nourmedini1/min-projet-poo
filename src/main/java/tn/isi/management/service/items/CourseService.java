package tn.isi.management.service.items;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Course;

import java.util.List;

@Service
public interface CourseService {
    Course addCourse(Course course);

    Course updateCourse(Course course);

    void deleteCourse(Integer id);

    Course getCourseById(Integer id);

    List<Course> getAllCourses();

    List<Course> searchCourses(
            String title,
            String titleContains,
            Integer minYear,
            Integer maxYear,
            Integer domainId,
            Integer minDuration,
            Integer maxDuration,
            Double minBudget,
            Double maxBudget
    );
    

}
