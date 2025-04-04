
package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Instructor;

import java.util.List;
import java.util.Optional;


@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    boolean existsByEmail(String email);

    Optional<Instructor> findByEmail(String email);

    Optional<Instructor> findByPhone(String phone);

    List<Instructor> findByEmployer_Id(Integer employerId);

    List<Instructor> findByType(String type);

    default Instructor createInstructor(Instructor instructor) {
        if (!existsByEmail(instructor.getEmail())) {
            return save(instructor);
        } else {
            throw new IllegalArgumentException("Instructor with this email already exists");
        }
    }

    default Instructor updateInstructor(Instructor instructor) {
        if (existsById(instructor.getId())) {
            return save(instructor);
        } else {
            throw new IllegalArgumentException("Instructor not found");
        }
    }

    default void deleteInstructor(Integer id) {
        if (existsById(id)) {
            deleteById(id);
        } else {
            throw new IllegalArgumentException("Instructor not found");
        }
    }
}