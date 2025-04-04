package tn.isi.management.application.items.v1.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Domain;
import tn.isi.management.domain.repositories.DomainRepository;
import tn.isi.management.service.items.DomainService;

import java.util.List;


@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    private DomainRepository domainRepository;

    @Override
    public Domain addDomain(Domain domain) {
        return domainRepository.createDomain(domain);
    }

    @Override
    public Domain updateDomain(Domain domain) {
        return domainRepository.save(domain);
    }

    @Override
    public void deleteDomain(Integer id) {
        domainRepository.deleteById(id);
    }

    @Override
    public Domain getDomainById(Integer id) {
        return domainRepository.findById(id).orElseThrow(() -> new RuntimeException("Domain not found"));
    }

    @Override
    public List<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    @Override
    public Domain getDomainByLabel(String label) {
        return domainRepository.findByLabel(label).orElseThrow(() -> new RuntimeException("Domain not found"));
    }

    @Override
    public List<Domain> getDomainsByLabelContaining(String label) {
        return domainRepository.findByLabelContaining(label);
    }
}
