package tn.isi.management.service.users;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(Integer id);

    User getUserById(Integer id);

    User getUserByLogin(String login);

    List<User> getUsersByRoleId(Integer roleId);

    List<User> getAllUsers();

    Optional<User> findByUsername(String username);
}
