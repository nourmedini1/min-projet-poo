package tn.isi.management.application.auth.v1.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.isi.management.application.auth.v1.models.SignInRequest;
import tn.isi.management.application.auth.v1.models.SignUpRequest;
import tn.isi.management.domain.entities.Role;
import tn.isi.management.domain.entities.User;
import tn.isi.management.domain.repositories.RoleRepository;

@Component
public class AuthMapper {

    @Autowired
    private  RoleRepository roleRepository;

    

    public User signInRequestToUser(SignInRequest signInRequest) {
        User user = new User();
        user.setLogin(signInRequest.getLogin());
        user.setPassword(signInRequest.getPassword());
        return user;
    }

    public User signUpRequestToUser(SignUpRequest signUpRequest) {
        Role role = roleRepository.findByName(signUpRequest.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + signUpRequest.getRoleName()));

        User user = new User();
        user.setLogin(signUpRequest.getLogin());
        user.setPassword(signUpRequest.getPassword());
        user.setRole(role);
        return user;
    }
}
