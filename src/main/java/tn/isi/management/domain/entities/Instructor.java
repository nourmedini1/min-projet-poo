package tn.isi.management.domain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email format")
    @Check(constraints = "email LIKE '%@%'")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email format")
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    @Pattern(regexp = "^[25789][0-9]{7}$", message = "Phone number must be 8 digits and start with 2, 5, 7, 8, or 9")
    private String phone;

    @Column(name = "type", nullable = false, updatable = false)
    @Check(constraints = "type IN ('INTERNAL', 'EXTERNAL')")
    private String type;

    @ManyToOne
    @JoinColumn(name = "idEmployer", nullable = false, updatable = false)
    private Employer employer;




}
