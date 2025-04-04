package tn.isi.management.domain.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "budget", nullable = false)
    private Double budget;

    @Column(name = "duration", nullable = false)
    private Integer durationInDays;

    @ManyToOne
    @JoinColumn(name = "idDomain", nullable = false)
    private Domain domain;

}
