package tn.isi.management.application.auth.v1.models;

import lombok.Data;

@Data
public class SignUpRequest {
    private String login;
    private String password;
    private String roleName;
}
