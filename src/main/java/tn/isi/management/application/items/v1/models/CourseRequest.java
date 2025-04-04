package tn.isi.management.application.items.v1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    private String title;
    private Integer year;
    private Double budget;
    private Integer durationInDays;
    private Integer domainId;
}
