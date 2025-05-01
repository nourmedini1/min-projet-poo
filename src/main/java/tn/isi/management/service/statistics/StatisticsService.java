package tn.isi.management.service.statistics;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface StatisticsService {

    HashMap<String , Integer> getStatistics();
}
