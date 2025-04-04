package tn.isi.management.application.actors.v1.models;


import lombok.Data;

@Data
public class CreateParticipantRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer structureId;
    private Integer profileId;
    private Integer courseId;
}
