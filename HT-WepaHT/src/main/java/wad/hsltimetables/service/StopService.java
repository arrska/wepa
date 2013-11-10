package wad.hsltimetables.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wad.hsltimetables.domain.Departure;
import wad.hsltimetables.domain.Line;
import wad.hsltimetables.domain.LineInfo;
import wad.hsltimetables.domain.Stop;


/* 
* Takes care of stop-related queries to timetableService
* so that timetableService.getfindStops(), getLine() and getStop()
* is called from outside of timetableService
* because of spring proxy mechanism, caching wouldn work otherwise
*/
@Service
public class StopService {
    @Autowired
    private TimetableService timetableService;
    
    public List<Stop> searchStop(String query) {
        return timetableService.findStops(query);
    }
    
    public Stop getStop(Integer stopCode) {
        Stop stop = timetableService.getStop(stopCode);
        
        if (stop==null) return null;
        
        List<Line> lines = new ArrayList<Line>();
        for (LineInfo lineinfo : stop.getLineInfos()) {
            Line line = timetableService.getLine(lineinfo.getLineCode());
            if (line != null)
                lines.add(line);
        }
        
        stop.setLines(lines);
        
        List<Departure> nextDeps = new ArrayList<Departure>();
        if (stop.getDepartures() != null) {
            for (Departure departure : stop.getDepartures()) {
                if (departure.getMinutesUntil() > -1) {
                    departure.setLine(timetableService.getLine(departure.getLineCode()));
                    nextDeps.add(departure);
                }
                if (nextDeps.size() == 10) break;
            }
            
            stop.setDepartures(nextDeps);
        }
        return stop;
    }
    
    public List<Stop> getStops(List<Integer> stopcodes) {
        if (stopcodes==null) return null;
        
        List<Stop> stops = new ArrayList<Stop>();

        for (Integer stopcode : stopcodes) {
            stops.add(this.getStop(stopcode));
        }

        return stops;
    }
    
    @Scheduled(fixedRate = 300000) //every 5 minutes
    @CacheEvict(value = "stops", allEntries = true)
    public void evictStopCache() {
        System.out.println("Stop cache evicter called");
    }
}
