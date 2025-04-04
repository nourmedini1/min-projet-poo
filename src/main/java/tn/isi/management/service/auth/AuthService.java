package tn.isi.management.service.auth;


import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.User;

@Service
public interface AuthService {

    User signIn(User user);
    User signUp(User user);
    User refreshAccessToken(String refreshToken);



}
