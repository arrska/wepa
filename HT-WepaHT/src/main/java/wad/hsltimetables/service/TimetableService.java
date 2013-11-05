package wad.hsltimetables.service;

import java.util.List;
import wad.hsltimetables.domain.Line;
import wad.hsltimetables.domain.Stop;

public interface TimetableService {
    List<Stop> findStops(String query);
    Stop getStop(Integer stopCode);
    Line getLine(String lineCode);
}
