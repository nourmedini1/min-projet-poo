package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Employer;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    Optional<Employer> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);

    default Employer updateEmployer(Employer employer) {
        if (existsById(employer.getId())) {
            return save(employer);
        } else {
            throw new IllegalArgumentException("Employer not found");
        }
    }

    List<Employer> findEmployerByNameContaining(String name);
}
