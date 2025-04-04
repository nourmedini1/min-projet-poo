package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    boolean existsByTitle(String title);

    void deleteByTitle(String title);

    default Course createCourse(Course course) {
        if (!existsByTitle(course.getTitle())) {
            return save(course);
        } else {
            throw new IllegalArgumentException("Course with this title already exists");
        }
    }


    @Query("SELECT c FROM Course c WHERE " +
           "(:title IS NULL OR c.title = :title) AND " +
           "(:titleContains IS NULL OR c.title LIKE %:titleContains%) AND " +
           "(:minYear IS NULL OR c.year >= :minYear) AND " +
           "(:maxYear IS NULL OR c.year <= :maxYear) AND " +
           "(:domainId IS NULL OR c.domain.id = :domainId) AND " +
           "(:minDuration IS NULL OR c.durationInDays >= :minDuration) AND " +
           "(:maxDuration IS NULL OR c.durationInDays <= :maxDuration) AND " +
           "(:minBudget IS NULL OR c.budget >= :minBudget) AND " +
           "(:maxBudget IS NULL OR c.budget <= :maxBudget)")
    List<Course> search(
            @Param("title") String title,
            @Param("titleContains") String titleContains,
            @Param("minYear") Integer minYear,
            @Param("maxYear") Integer maxYear,
            @Param("domainId") Integer domainId,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration,
            @Param("minBudget") Double minBudget,
            @Param("maxBudget") Double maxBudget
    );

   
}