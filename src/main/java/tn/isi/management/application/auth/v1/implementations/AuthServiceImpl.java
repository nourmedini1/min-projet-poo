package tn.isi.management.application.auth.v1.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.RefreshToken;
import tn.isi.management.domain.entities.User;
import tn.isi.management.domain.repositories.UserRepository;
import tn.isi.management.service.auth.AuthService;
import tn.isi.management.service.auth.JwtService;
import tn.isi.management.service.auth.RefreshTokenService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public User signIn(User user) {
        logger.info("Login attempt for user: {}", user.getLogin());

        try {
            validateUserExists(user.getLogin());
            authenticateUser(user.getLogin(), user.getPassword());

            User authenticatedUser = findUserByLogin(user.getLogin());
            applyTokensToUser(authenticatedUser);

            logger.info("Login successful for user: {}", user.getLogin());
            return authenticatedUser;
        } catch (BadCredentialsException e) {
            logger.warn("Login failed: Bad credentials for user: {}", user.getLogin());
            throw new BadCredentialsException("Invalid credentials");
        } catch (Exception e) {
            logger.error("Login error for user {}: {}", user.getLogin(), e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User signUp(User user) {
        logger.info("Registration attempt for login: {}", user.getLogin());

        if (userRepository.existsByLogin(user.getLogin())) {
            logger.warn("Registration failed: User already exists: {}", user.getLogin());
            throw new RuntimeException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: {}", savedUser.getLogin());

        applyTokensToUser(savedUser);
        return savedUser;
    }

    @Override
    public User refreshAccessToken(String refreshToken) {
        logger.info("Token refresh attempt");

        validateRefreshToken(refreshToken);

        String username = jwtService.getUsernameFromToken(refreshToken);
        User user = findUserByLogin(username);

        verifyStoredToken(user, refreshToken);

        String newAccessToken = jwtService.generateAccessToken(user);
        user.setAccessToken(newAccessToken);
        user.setRefreshToken(refreshToken);

        return user;
    }



    private void validateUserExists(String login) {
        if (!userRepository.existsByLogin(login)) {
            logger.warn("User not found: {}", login);
            throw new RuntimeException("User not found");
        }
    }

    private void authenticateUser(String login, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.debug("Authentication successful for: {}", login);
    }

    private User findUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void applyTokensToUser(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
    }

    private void validateRefreshToken(String refreshToken) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            logger.warn("Invalid refresh token");
            throw new RuntimeException("Invalid refresh token");
        }
    }

    private void verifyStoredToken(User user, String refreshToken) {
        Optional<RefreshToken> storedToken = refreshTokenService.findByUserId(user.getId());

        if (storedToken.isEmpty() || !storedToken.get().getToken().equals(refreshToken)) {
            logger.warn("Refresh token not found or doesn't match");
            throw new RuntimeException("Invalid refresh token");
        }

        refreshTokenService.verifyExpiration(storedToken.get());
    }
}