package tn.isi.management.application.actors.v1.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Employer;
import tn.isi.management.domain.repositories.EmployerRepository;
import tn.isi.management.service.actors.EmployerService;

import java.util.List;


@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;
    @Override
    public Employer addEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    @Override
    public Employer updateEmployer(Employer employer) {
        return employerRepository.updateEmployer(employer);
    }

    @Override
    public void deleteEmployer(Integer id) {
        employerRepository.deleteById(id);
    }

    @Override
    public Employer getEmployerById(Integer id) {
        return employerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employer not found"));
    }

    @Override
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    @Override
    public Employer getEmployerByName(String name) {
        return employerRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Employer not found"));

    }

    @Override
    public List<Employer> getEmployersByNameContaining(String name) {
        return employerRepository.findEmployerByNameContaining(name);
    }
}
