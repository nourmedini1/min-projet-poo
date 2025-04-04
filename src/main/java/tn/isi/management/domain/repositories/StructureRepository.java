package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Structure;

import java.util.Optional;


@Repository
public interface StructureRepository extends JpaRepository<Structure, Integer> {

    Optional<Structure> findByLabel(String name);

    boolean existsByLabel(String name);

    void deleteByLabel(String name);

    default Structure createStructure(Structure structure) {
        if (!existsByLabel(structure.getLabel())) {
            return save(structure);
        } else {
            throw new IllegalArgumentException("Structure with this name already exists");
        }
    }
}
