package tn.isi.management.service.items;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Role;

import java.util.List;

@Service
public interface RoleService {

    Role addRole(Role role);

    Role updateRole(Role role);

    void deleteRole(Integer id);

    Role getRoleById(Integer id);

    Role getRoleByName(String name);

    List<Role> getAllRoles();

}
