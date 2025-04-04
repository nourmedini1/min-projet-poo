package tn.isi.management.application.actors.v1.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Participant;
import tn.isi.management.domain.repositories.ParticipantRepository;
import tn.isi.management.service.actors.ParticipantService;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public Participant addParticipant(Participant participant) {
        return participantRepository.createParticipant(participant);
    }

    @Override
    public Participant updateParticipant(Participant participant) {
        return participantRepository.updateParticipant(participant);
    }

    @Override
    public void deleteParticipant(Integer id) {
        participantRepository.deleteParticipant(id);
    }

    @Override
    public Participant getParticipantById(Integer id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));
    }

    @Override
    public Participant getParticipantByEmail(String email) {
        return participantRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));
    }

    @Override
    public Participant getParticipantByPhone(String phone) {
        return participantRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));
    }

    @Override
    public List<Participant> getParticipantsByStructureId(Integer structureId) {
        return participantRepository.findByStructure_Id(structureId);
    }

    @Override
    public List<Participant> getParticipantsByProfileId(Integer profileId) {
        return participantRepository.findByProfile_Id(profileId);
    }

    @Override
    public List<Participant> getParticipantsByCourseId(Integer courseId) {
        return participantRepository.findByCourse_Id(courseId);
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }
}
