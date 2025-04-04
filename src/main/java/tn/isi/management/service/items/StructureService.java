package tn.isi.management.service.items;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Structure;

import java.util.List;

@Service
public interface StructureService {

    Structure addStructure(Structure structure);

    Structure updateStructure(Structure structure);

    void deleteStructure(Integer id);

    Structure getStructureById(Integer id);

    Structure getStructureByLabel(String label);

    List<Structure> getAllStructures();


}
