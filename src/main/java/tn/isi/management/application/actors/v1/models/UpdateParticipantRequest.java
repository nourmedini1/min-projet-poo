package tn.isi.management.application.actors.v1.models;


import lombok.Data;

@Data
public class UpdateParticipantRequest {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer employerId;
    private Integer structureId;
    private Integer profileId;
    private Integer courseId;
}
