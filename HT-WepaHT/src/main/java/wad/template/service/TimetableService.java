package wad.template.service;

import java.util.List;
import wad.template.domain.Line;
import wad.template.domain.Stop;

public interface TimetableService {
    List<Stop> findStops(String query);
    Stop getStop(Integer stopCode);
    Line getLine(String lineCode);
}
