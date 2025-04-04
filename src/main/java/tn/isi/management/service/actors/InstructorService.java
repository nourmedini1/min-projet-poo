package tn.isi.management.service.actors;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Instructor;

import java.util.List;

@Service
public interface InstructorService {

    Instructor addInstructor(Instructor instructor);

    Instructor updateInstructor(Instructor instructor);

    void deleteInstructor(Integer id);

    Instructor getInstructorById(Integer id);

    Instructor getInstructorByEmail(String email);

    Instructor getInstructorByPhone(String phone);

    List<Instructor> getInstructorsByType(String type);

    List<Instructor> getAllInstructors();

    List<Instructor> getInstructorsByEmployerId(Integer employerId);

}
