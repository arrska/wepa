package wad.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import wad.template.data.deseriaizer.CoordinateDeserializer;
import wad.template.data.deseriaizer.LineInfoDeserializer;
import wad.template.data.jsonview.JsonViews;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stop {
    @JsonProperty("code")
    private Integer code;
    
    @JsonProperty("code_short")
    private String shortCode;
    private String name;
    private String address;
    private String city;
    private Coordinates coordinates;
    private List<Departure> departures;
    private List<LineInfo> lineInfos;
    
    @JsonIgnore
    private List<Line> lines;
    
    @JsonProperty("lines")
    @JsonView(JsonViews.DefaultStopView.DetailedStopView.class)
    public List<LineInfo> getLineInfos() {
        return lineInfos;
    }

    @JsonProperty("lines")
    @JsonDeserialize(using = LineInfoDeserializer.class)
    public void setLineInfos(List<LineInfo> lineInfos) {
        this.lineInfos = lineInfos;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
    
    @JsonProperty("coordinates")
    @JsonView(JsonViews.DefaultStopView.class)
    public Coordinates getCoordinates() {
        return coordinates;
    }
    
    @JsonProperty("wgs_coords")
    @JsonDeserialize(using = CoordinateDeserializer.class)
    public void setwgsCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonView(JsonViews.DefaultStopView.DetailedStopView.class)
    public List<Departure> getDepartures() {
        return departures;
    }

    @JsonProperty("departures")
    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    @JsonView(JsonViews.DefaultStopView.class)
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonView(JsonViews.DefaultStopView.class)
    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @JsonProperty("name")
    @JsonView(JsonViews.DefaultStopView.class)
    public String getName() {
        return name;
    }

    @JsonProperty("name_fi")
    public void setNameFi(String name) {
        this.name = name;
    }

    @JsonProperty("city")
    @JsonView(JsonViews.DefaultStopView.class)
    public String getCity() {
        return city;
    }

    @JsonProperty("city_fi")
    public void setCityFi(String city) {
        this.city = city;
    }

    @JsonProperty("address")
    @JsonView(JsonViews.DefaultStopView.class)
    public String getAddress() {
        return address;
    }

    @JsonProperty("address_fi")
    public void setAddressFi(String address) {
        this.address = address;
    }
}
