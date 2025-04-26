package tn.isi.management.application.items.v1.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.isi.management.application.items.v1.models.*;
import tn.isi.management.domain.entities.*;
import tn.isi.management.domain.repositories.DomainRepository;

@Component
public class ItemsMapper {

    @Autowired
    private  DomainRepository domainRepository;



    public Domain toDomain(DomainRequest domainRequest) {
        if (domainRequest == null) {
            return null;
        }
        Domain domain = new Domain();
        domain.setLabel(domainRequest.getLabel());
        return domain;
    }

    public Course toCourse(CourseRequest courseRequest) {
        if (courseRequest == null) {
            return null;
        }
        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setYear(courseRequest.getYear());
        course.setBudget(courseRequest.getBudget());
        course.setDurationInDays(courseRequest.getDurationInDays());
        if (courseRequest.getDomainId() != null) {
            Domain domain = domainRepository.findById(courseRequest.getDomainId())
                    .orElseThrow(() -> new RuntimeException("Domain not found with id: " + courseRequest.getDomainId()));
            course.setDomain(domain);
        }
        return course;
    }

    public Profile toProfile(ProfileRequest request) {
        if (request == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setLabel(request.getLabel());
        return profile;
    }

    public Structure toStructure(StructureRequest request) {
        if (request == null) {
            return null;
        }
        Structure structure = new Structure();
        structure.setLabel(request.getLabel());
        return structure;
    }

    public Role toRole(RoleRequest request) {
        if (request == null) {
            return null;
        }
        Role role = new Role();
        role.setName(request.getName());
        return role;
    }
}
