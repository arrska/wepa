package wad.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import wad.template.data.LineDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stop {
    private Integer code;
    
    @JsonProperty("code_short")
    private String shortCode;
    
    @JsonProperty("name_fi")
    private String name;
    
    @JsonProperty("name_sv")
    private String nameSv;
    
    @JsonProperty("city_fi")
    private String cityFi;
    
    @JsonProperty("city_sv")
    private String citySv;

    @JsonProperty("wgs_coords")
    private String coordinates;

    @JsonProperty("timetable_link")
    private String timetableLink;
    
    @JsonProperty("address_fi")
    private String address;
    
    @JsonProperty("address_sv")
    private String addressSv;
    
    @JsonProperty("departures")
    private List<Departure> departures;
    
    @JsonProperty("lines")
    private List<Line> lines;

    public Stop() {
    }
    
    public String getLatitude() {
        if (coordinates == null) {
            return null;
        }
        return coordinates.split(",")[1];
    }
    
    public String getLongtitude() {
        if (coordinates == null) {
            return null;
        }
        return coordinates.split(",")[0];
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    public List<Line> getLines() {
        return lines;
    }

    @JsonDeserialize(using = LineDeserializer.class)
    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNameSv() {
        return nameSv;
    }

    public void setNameSv(String nameSv) {
        this.nameSv = nameSv;
    }

    public String getCityFi() {
        return cityFi;
    }

    public void setCityFi(String cityFi) {
        this.cityFi = cityFi;
    }

    public String getCitySv() {
        return citySv;
    }

    public void setCitySv(String citySv) {
        this.citySv = citySv;
    }


    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    
    public String getTimetableLink() {
        return timetableLink;
    }

    public void setTimetableLink(String timetableLink) {
        this.timetableLink = timetableLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressSv() {
        return addressSv;
    }

    public void setAddressSv(String addressSv) {
        this.addressSv = addressSv;
    }

}
