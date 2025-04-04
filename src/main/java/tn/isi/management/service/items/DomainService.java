package tn.isi.management.service.items;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Domain;

import java.util.List;

@Service
public interface DomainService {

    Domain addDomain(Domain domain);

    Domain updateDomain(Domain domain);

    void deleteDomain(Integer id);

    Domain getDomainById(Integer id);

    List<Domain> getAllDomains();

    Domain getDomainByLabel(String label);

    List<Domain> getDomainsByLabelContaining(String label);

}
