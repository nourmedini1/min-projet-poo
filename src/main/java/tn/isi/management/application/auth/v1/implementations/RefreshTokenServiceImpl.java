package tn.isi.management.application.auth.v1.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.RefreshToken;
import tn.isi.management.domain.entities.User;
import tn.isi.management.domain.repositories.RefreshTokenRepository;
import tn.isi.management.service.auth.RefreshTokenService;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;



    @Override
    @Transactional
    public void createRefreshToken(String token, User user) {
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser_Id(user.getId());
        RefreshToken refreshToken;
        if (existingToken.isPresent()) {
            refreshToken = existingToken.get();
            refreshToken.setToken(token);
            refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        } else {
            refreshToken = new RefreshToken();
            refreshToken.setToken(token);
            refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
            refreshToken.setUser(user);
        }
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByUserId(Integer userId) {
        return refreshTokenRepository.findByUser_Id(userId);
    }

    @Override
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token has expired. Please log in again.");
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Integer userId) {
        refreshTokenRepository.deleteByUser_Id(userId);
    }

    @Override
    public RefreshToken updateRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }
}
