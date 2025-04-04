package tn.isi.management.service.auth;

import io.jsonwebtoken.Claims;
import tn.isi.management.domain.entities.User;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String getUsernameFromToken(String token);

    Date getExpirationDateFromToken(String token);

    String getRoleFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    boolean validateRefreshToken(String token);

    boolean validateToken(String token);
}