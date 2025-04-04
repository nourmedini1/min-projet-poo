package tn.isi.management.application.items.v1.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.isi.management.application.items.v1.models.*;
import tn.isi.management.domain.entities.*;
import tn.isi.management.domain.repositories.DomainRepository;

@Component
public abstract class ItemsMapper {


    private  static DomainRepository domainRepository;

    @Autowired
    public ItemsMapper(DomainRepository domainRepository) {
        ItemsMapper.domainRepository = domainRepository;
    }

    public static Domain ToDomain(DomainRequest domainRequest) {
        if (domainRequest == null) {
            return null;
        }

        Domain domain = new Domain();
        domain.setLabel(domainRequest.getLabel());
        return domain;
    }

    public static Course ToCourse(CourseRequest courseRequest) {
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

    public static Profile toProfile(ProfileRequest request) {
        if (request == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setLabel(request.getLabel());
        return profile;
    }

    public static Structure toStructure(StructureRequest request) {
        if (request == null) {
            return null;
        }
        Structure structure = new Structure();
        structure.setLabel(request.getLabel());
        return structure;
    }

    public static Role toRole(RoleRequest request) {
        if (request == null) {
            return null;
        }
        Role role = new Role();
        role.setName(request.getName());
        return role;
    }




}
