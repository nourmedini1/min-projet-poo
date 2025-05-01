package tn.isi.management.application.statistics.v1.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.isi.management.service.statistics.StatisticsService;

import java.util.HashMap;

@RestController
@RequestMapping(StatisticsResource.CONTEXT_PATH)
public class StatisticsResource {

    public static final String CONTEXT_PATH = "/statistics/v1";

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("manager/all")
    public ResponseEntity<?> getAllStatistics() {
        try {
            HashMap<String, Integer> statistics = statisticsService.getStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching statistics: " + e.getMessage());
        }
    }
}
