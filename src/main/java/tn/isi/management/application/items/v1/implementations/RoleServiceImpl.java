package tn.isi.management.application.items.v1.implementations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Role;
import tn.isi.management.domain.repositories.RoleRepository;
import tn.isi.management.service.items.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return roleRepository.createRole(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.save(role);

    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
