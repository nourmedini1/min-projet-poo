package tn.isi.management.application.auth.v1.models;


import lombok.*;

@Data
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String login;
    private String roleName;
}
