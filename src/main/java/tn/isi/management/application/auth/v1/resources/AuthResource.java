package tn.isi.management.application.auth.v1.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import tn.isi.management.application.auth.v1.mappers.AuthMapper;
import tn.isi.management.application.auth.v1.models.AuthResponse;
import tn.isi.management.application.auth.v1.models.SignInRequest;
import tn.isi.management.application.auth.v1.models.SignUpRequest;
import tn.isi.management.domain.entities.User;
import tn.isi.management.domain.repositories.UserRepository;

import tn.isi.management.service.auth.AuthService;
import tn.isi.management.service.auth.JwtService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AuthResource.CONTEXT_PATH)
public class AuthResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);

    public static final String CONTEXT_PATH = "/auth/v1";

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthMapper authMapper;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        try {
            User userToAuthenticate = authMapper.signInRequestToUser(signInRequest);
            User authenticatedUser = authService.signIn(userToAuthenticate);
            return ResponseEntity.ok(createAuthResponse(authenticatedUser));
        } catch (BadCredentialsException e) {
            return createErrorResponse("Invalid credentials");
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            User userToRegister = authMapper.signUpRequestToUser(signUpRequest);
            User registeredUser = authService.signUp(userToRegister);

            return ResponseEntity.ok(createAuthResponse(registeredUser));
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            String refreshToken = extractRefreshTokenFromCookies(request);

            if (refreshToken == null) {
                return createErrorResponse("Refresh token not found");
            }
            User refreshedUser = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(createAuthResponse(refreshedUser));
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }




    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private AuthResponse createAuthResponse(User user) {
        return AuthResponse.builder()
                .accessToken(user.getAccessToken())
                .refreshToken(user.getRefreshToken())
                .login(user.getLogin())
                .roleName(user.getRole().getName())
                .build();
    }

    private ResponseEntity<?> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return ResponseEntity.badRequest().body(error);
    }
}