package wad.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

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

    @JsonProperty(value = "line_stops")
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

    public List<LineStop> getStops() {
        return stops;
    }

    public void setStops(List<LineStop> stops) {
        this.stops = stops;
    }
}
