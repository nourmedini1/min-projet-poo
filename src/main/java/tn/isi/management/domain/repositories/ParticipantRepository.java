package tn.isi.management.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.Participant;

import java.util.List;
import java.util.Optional;


@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    Optional<Participant> findByPhone(String phone);

    Optional<Participant> findByEmail(String email);

    List<Participant> findByStructure_Id(Integer structureId);

    List<Participant> findByProfile_Id(Integer profileId);

    List<Participant> findByCourse_Id(Integer courseId);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    default Participant createParticipant(Participant participant) {
        if (!existsByEmail(participant.getEmail()) && !existsByPhone(participant.getPhone())) {
            return save(participant);
        } else {
            throw new IllegalArgumentException("Participant with this email or phone already exists");
        }
    }

    default Participant updateParticipant(Participant participant) {
        if (existsById(participant.getId())) {
            return save(participant);
        } else {
            throw new IllegalArgumentException("Participant not found");
        }
    }

    default void deleteParticipant(Integer id) {
        if (existsById(id)) {
            deleteById(id);
        } else {
            throw new IllegalArgumentException("Participant not found");
        }
    }
}