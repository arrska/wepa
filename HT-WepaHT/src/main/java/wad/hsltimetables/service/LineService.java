package wad.hsltimetables.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wad.hsltimetables.domain.Line;

@Service
public class LineService {
    @Autowired
    private TimetableService timetableService;
    
    public Line getLine(String lineCode) {
        return timetableService.getLine(lineCode);
    }
    
    @Scheduled(cron = "1 1 * * * *") //every hour
    @CacheEvict(value = "lines", allEntries = true)
    public void evictLineCache() {
//        System.out.println("Line cache evicter called");
    }
}
