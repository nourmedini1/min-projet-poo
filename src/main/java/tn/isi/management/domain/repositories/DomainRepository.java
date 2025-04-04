package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Domain;

import java.util.List;
import java.util.Optional;


@Repository
public interface DomainRepository extends JpaRepository<Domain, Integer> {

    Optional<Domain> findByLabel(String name);

    boolean existsByLabel(String name);

    void deleteByLabel(String name);

    default Domain createDomain(Domain domain) {
        if (!existsByLabel(domain.getLabel())) {
            return save(domain);
        } else {
            throw new IllegalArgumentException("Domain with this name already exists");
        }
    }

    List<Domain> findByLabelContaining(String label);
}