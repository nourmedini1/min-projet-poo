package tn.isi.management.application.auth.v1.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.User;
import tn.isi.management.service.auth.JwtService;
import tn.isi.management.service.auth.RefreshTokenService;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public JwtServiceImpl() {
        try {
            privateKey = extractPrivateKey(new ClassPathResource("private.pem"));
            publicKey = extractPublicKey(new ClassPathResource("public.pem"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA keys", e);
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @Override
    public String getRoleFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("role", String.class);
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getName());
        return generateToken(claims, user.getLogin(), JWT_TOKEN_VALIDITY);
    }

    @Override
    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        String token = generateToken(claims, user.getLogin(), REFRESH_TOKEN_VALIDITY);

        try {
            refreshTokenService.createRefreshToken(token, user);
        } catch (Exception e) {
            System.err.println("Failed to store refresh token: " + e.getMessage());
        }

        return token;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            if (!validateToken(token)) {
                return false;
            }

            Claims claims = getAllClaimsFromToken(token);
            return "refresh".equals(claims.get("type", String.class));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            getAllClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateToken(Map<String, Object> claims, String subject, long validity) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(privateKey)
                .compact();
    }

    private PrivateKey extractPrivateKey(ClassPathResource privateKeyResource) throws Exception {
        String privateKeyContent = new String(Files.readAllBytes(privateKeyResource.getFile().toPath()));
        privateKeyContent = privateKeyContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    private PublicKey extractPublicKey(ClassPathResource publicKeyResource) throws Exception {
        String publicKeyContent = new String(Files.readAllBytes(publicKeyResource.getFile().toPath()));
        publicKeyContent = publicKeyContent
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }
}