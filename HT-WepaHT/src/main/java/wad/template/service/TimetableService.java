package wad.template.service;

import java.util.List;
import wad.template.domain.Line;
import wad.template.domain.LineInfo;
import wad.template.domain.Stop;

public interface TimetableService {
    List<Stop> getStops(String query, boolean withLines);
    Stop getStop(Integer stopCode, boolean withLines);
    
    Line getLine(String lineCode);
    Line getLine(LineInfo lineinfo);
    List<Line> getLines(List<LineInfo> lineinfos);
    List<Line> getLines(String query);
}
