package tn.isi.management.application.actors.v1.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.isi.management.application.actors.v1.models.*;
import tn.isi.management.domain.entities.Employer;
import tn.isi.management.domain.entities.Instructor;
import tn.isi.management.domain.entities.Participant;
import tn.isi.management.domain.repositories.CourseRepository;
import tn.isi.management.domain.repositories.EmployerRepository;
import tn.isi.management.domain.repositories.ProfileRepository;
import tn.isi.management.domain.repositories.StructureRepository;

@Component
public class ActorsMapper {

    @Autowired
    private  StructureRepository structureRepository;
    @Autowired
    private  ProfileRepository profileRepository;
    @Autowired
    private  CourseRepository courseRepository;
    @Autowired
    private  EmployerRepository employerRepository;

    

    public Participant toParticipant(CreateParticipantRequest request) {
        Participant participant = new Participant();
        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setEmail(request.getEmail());
        participant.setPhone(request.getPhone());
        participant.setStructure(structureRepository.findById(request.getStructureId())
                .orElseThrow(() -> new IllegalArgumentException("Structure not found")));
        participant.setProfile(profileRepository.findById(request.getProfileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found")));
        participant.setCourse(courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found")));
        return participant;
    }

    public Employer toEmployer(CreateEmployerRequest request) {
        Employer employer = new Employer();
        employer.setName(request.getName());
        return employer;
    }

    public Instructor toInstructor(CreateInstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setEmail(request.getEmail());
        instructor.setPhone(request.getPhone());
        instructor.setType(request.getType());
        instructor.setEmployer(employerRepository.findById(request.getEmployerId())
                .orElseThrow(() -> new IllegalArgumentException("Employer not found")));
        return instructor;
    }

    public Instructor updateInstructorRequestToInstructor(UpdateInstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setId(request.getId());
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setEmail(request.getEmail());
        instructor.setPhone(request.getPhone());
        instructor.setType(request.getType());
        instructor.setEmployer(employerRepository.findById(request.getEmployerId())
                .orElseThrow(() -> new IllegalArgumentException("Employer not found")));
        return instructor;
    }

    public Employer updateEmployerRequestToEmployer(UpdateEmployerRequest request) {
        Employer employer = new Employer();
        employer.setId(request.getId());
        employer.setName(request.getName());
        return employer;
    }

    public Participant updateParticipantRequestToParticipant(UpdateParticipantRequest updateParticipantRequest) {
        Participant participant = new Participant();
        participant.setId(updateParticipantRequest.getId());
        participant.setFirstName(updateParticipantRequest.getFirstName());
        participant.setLastName(updateParticipantRequest.getLastName());
        participant.setEmail(updateParticipantRequest.getEmail());
        participant.setPhone(updateParticipantRequest.getPhone());
        participant.setStructure(structureRepository.findById(updateParticipantRequest.getStructureId())
                .orElseThrow(() -> new IllegalArgumentException("Structure not found")));
        participant.setProfile(profileRepository.findById(updateParticipantRequest.getProfileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found")));
        participant.setCourse(courseRepository.findById(updateParticipantRequest.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found")));
        return participant;
    }
}
