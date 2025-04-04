package tn.isi.management.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false, updatable = false)
    private Role role;

    // Transient fields for tokens (not stored in database)
    @Transient
    private String accessToken;
    
    @Transient
    private String refreshToken;
}
