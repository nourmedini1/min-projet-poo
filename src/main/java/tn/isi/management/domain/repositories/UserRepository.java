package tn.isi.management.domain.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.isi.management.domain.entities.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);


    default User updateUser(User user) {
        if (existsById(user.getId())) {
            return save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    default User createUser(User user) {
        if (!existsByLogin(user.getLogin())) {
            return save(user);
        } else {
            throw new IllegalArgumentException("User with this login already exists");
        }
    }

    default void deleteUserById(@NotNull Integer id) {
        if (existsById(id)) {
            deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // Method to find users by role
    List<User> findByRole_Id(Integer roleId);

}