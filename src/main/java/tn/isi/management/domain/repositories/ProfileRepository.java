package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Profile;

import java.util.Optional;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findByLabel(String name);

    boolean existsByLabel(String name);

    void deleteByLabel(String name);

    default Profile createProfile(Profile profile) {
        if (!existsByLabel(profile.getLabel())) {
            return save(profile);
        } else {
            throw new IllegalArgumentException("Profile with this name already exists");
        }
    }

    default Profile updateProfile(Profile profile) {
        if (existsById(profile.getId())) {
            return save(profile);
        } else {
            throw new IllegalArgumentException("Profile not found");
        }
    }
}
