package tn.isi.management.application.users.v1.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String login;
    private String roleName;
}
