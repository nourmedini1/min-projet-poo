package tn.isi.management.domain.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token", nullable = false, unique = true, columnDefinition = "TEXT")  // Using TEXT type for unlimited length
    private String token;

    @Column(name = "expiresAt", nullable = false)
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;
    
}
