package tn.isi.management.application.items.v1.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Structure;
import tn.isi.management.domain.repositories.StructureRepository;
import tn.isi.management.service.items.StructureService;

import java.util.List;


@Service
public class StructureServiceImpl implements StructureService {

    @Autowired
    private StructureRepository structureRepository;


    @Override
    public Structure addStructure(Structure structure) {
        return structureRepository.createStructure(structure);
    }

    @Override
    public Structure updateStructure(Structure structure) {
        return structureRepository.save(structure);
    }

    @Override
    public void deleteStructure(Integer id) {
        structureRepository.deleteById(id);
    }
    @Override
    public Structure getStructureById(Integer id) {
        return structureRepository.findById(id).orElseThrow(() -> new RuntimeException("Structure not found"));
    }

    @Override
    public Structure getStructureByLabel(String label) {
        return structureRepository.findByLabel(label).orElseThrow(() -> new RuntimeException("Structure not found"));
    }

    @Override
    public List<Structure> getAllStructures() {
        return structureRepository.findAll();
    }
}
