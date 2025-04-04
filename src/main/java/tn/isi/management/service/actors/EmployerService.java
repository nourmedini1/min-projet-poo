package tn.isi.management.service.actors;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Employer;

import java.util.List;

@Service
public interface EmployerService {

    Employer addEmployer(Employer employer);

    Employer updateEmployer(Employer employer);

    void deleteEmployer(Integer id);

    Employer getEmployerById(Integer id);

    List<Employer> getAllEmployers();

    Employer getEmployerByName(String name);

    List<Employer> getEmployersByNameContaining(String name);
}
