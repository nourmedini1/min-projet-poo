package tn.isi.management.service.actors;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Participant;

import java.util.List;


@Service
public interface ParticipantService {

    Participant addParticipant(Participant participant);

    Participant updateParticipant(Participant participant);

    void deleteParticipant(Integer id);

    Participant getParticipantById(Integer id);

    Participant getParticipantByEmail(String email);

    Participant getParticipantByPhone(String phone);

    List<Participant> getParticipantsByStructureId(Integer structureId);

    List<Participant> getParticipantsByProfileId(Integer profileId);

    List<Participant> getParticipantsByCourseId(Integer courseId);

    List<Participant> getAllParticipants();
}