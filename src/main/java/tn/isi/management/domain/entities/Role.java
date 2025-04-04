package tn.isi.management.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    @Check(constraints = "name IN ('ADMIN', 'USER', 'MANAGER')")
    private String name;


}
