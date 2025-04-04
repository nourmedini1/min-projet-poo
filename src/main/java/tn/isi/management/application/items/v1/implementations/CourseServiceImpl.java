package tn.isi.management.application.items.v1.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Course;
import tn.isi.management.domain.repositories.CourseRepository;
import tn.isi.management.service.items.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course addCourse(Course course) {
        return courseRepository.createCourse(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course getCourseById(Integer id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> searchCourses(
            String title,
            String titleContains,
            Integer minYear,
            Integer maxYear,
            Integer domainId,
            Integer minDuration,
            Integer maxDuration,
            Double minBudget,
            Double maxBudget
    ) {
        logger.debug("Searching courses with criteria: title={}, titleContains={}, minYear={}, maxYear={}, " +
                     "domainId={}, minDuration={}, maxDuration={}, minBudget={}, maxBudget={}",
                title, titleContains, minYear, maxYear, domainId, minDuration, maxDuration, minBudget, maxBudget);
        
        return courseRepository.search(
                title, titleContains, minYear, maxYear, domainId, 
                minDuration, maxDuration, minBudget, maxBudget
        );
    }

    

}
