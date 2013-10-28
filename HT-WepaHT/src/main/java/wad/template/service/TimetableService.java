package wad.template.service;

import java.util.List;
import wad.template.domain.LineInfo;
import wad.template.domain.Stop;

public interface TimetableService {
    List<Stop> getStops(String query);
    Stop getStop(Integer stopCode);
    LineInfo getLineInfo(Integer lineCode);
    Line getLine(Integer lineCode);
}
