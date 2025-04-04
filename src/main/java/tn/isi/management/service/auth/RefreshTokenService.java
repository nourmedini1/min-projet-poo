package tn.isi.management.service.auth;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.RefreshToken;
import tn.isi.management.domain.entities.User;

import java.util.Optional;

@Service
public interface RefreshTokenService {
    void createRefreshToken(String token, User user);
    Optional<RefreshToken> findByUserId(Integer userId);
    void verifyExpiration(RefreshToken token);
    void deleteByUserId(Integer userId);
    RefreshToken updateRefreshToken(RefreshToken refreshToken);

}
