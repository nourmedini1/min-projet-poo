package tn.isi.management.application.statistics.v1.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.service.actors.EmployerService;
import tn.isi.management.service.actors.InstructorService;
import tn.isi.management.service.actors.ParticipantService;
import tn.isi.management.service.items.CourseService;
import tn.isi.management.service.statistics.StatisticsService;

import java.util.HashMap;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private InstructorService instructorService;


    @Override
    public HashMap<String, Integer> getStatistics() {

        int totalCourses = courseService.getAllCourses().size();
        int totalEmployers = employerService.getAllEmployers().size();
        int totalParticipants = participantService.getAllParticipants().size();
        int totalInstructors = instructorService.getAllInstructors().size();

        HashMap<String, Integer> statistics = new HashMap<>();
        statistics.put("totalCourses", totalCourses);
        statistics.put("totalEmployers", totalEmployers);
        statistics.put("totalParticipants", totalParticipants);
        statistics.put("totalInstructors", totalInstructors);
        return statistics;
    }
}
