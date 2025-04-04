package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Role;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByName(String name);

    void deleteByName(String name);

    default Role createRole(Role role) {
        if (!existsByName(role.getName())) {
            return save(role);
        } else {
            throw new IllegalArgumentException("Role with this name already exists");
        }
    }

    Optional<Role> findByName(String name);






}
