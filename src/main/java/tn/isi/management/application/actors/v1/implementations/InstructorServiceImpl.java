package tn.isi.management.application.actors.v1.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Instructor;
import tn.isi.management.domain.repositories.InstructorRepository;
import tn.isi.management.service.actors.InstructorService;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.createInstructor(instructor);
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorRepository.updateInstructor(instructor);
    }

    @Override
    public void deleteInstructor(Integer id) {
        instructorRepository.deleteInstructor(id);
    }

    @Override
    public Instructor getInstructorById(Integer id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
    }

    @Override
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
    }

    @Override
    public Instructor getInstructorByPhone(String phone) {
        return instructorRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
    }

    @Override
    public List<Instructor> getInstructorsByType(String type) {
        return instructorRepository.findByType(type);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public List<Instructor> getInstructorsByEmployerId(Integer employerId) {
        return instructorRepository.findByEmployer_Id(employerId);
    }

}
