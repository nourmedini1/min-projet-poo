package tn.isi.management.application.actors.v1.models;


import lombok.Data;
import tn.isi.management.domain.entities.Employer;

@Data
public class UpdateInstructorRequest {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String type;
    private Integer employerId;
}
