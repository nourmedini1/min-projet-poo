package tn.isi.management.application.actors.v1.models;


import lombok.Data;

@Data
public class CreateInstructorRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String type;
    private Integer employerId;
}
