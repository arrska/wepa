package wad.hsltimetables.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import wad.hsltimetables.service.HSLTimetableService;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Line {
    @JsonProperty(value = "code")
    private String code;
    
    @JsonProperty(value = "name")
    private String name;
    
    @JsonProperty(value = "code_short")
    private String shortCode;
    
    @JsonProperty(value = "transport_type_id")
    private Integer transportType;
    
    @JsonProperty(value = "line_start")
    private String start;
    
    @JsonProperty(value = "line_end")
    private String end;

    private List<LineStop> stops;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Integer getTransportType() {
        return transportType;
    }
    
    @JsonIgnore
    public String getTransportTypeName() {
        return HSLTimetableService.TransportType(this.transportType);
    }

    public void setTransportType(Integer transportType) {
        this.transportType = transportType;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @JsonIgnore(value = true)
    public List<LineStop> getStops() {
        return stops;
    }

    @JsonProperty(value = "line_stops")
    public void setStops(List<LineStop> stops) {
        this.stops = stops;
    }
}
