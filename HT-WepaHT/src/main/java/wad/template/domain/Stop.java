package wad.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import wad.template.data.CoordinateDeserializer;
import wad.template.data.LineDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "stops")
public class Stop implements Serializable {
    @Id
    @Column(name = "stopcode")
    private Integer code;
    
    @JsonProperty("code_short")
    @Column(name = "shortcode")
    private String shortCode;
    
    @JsonProperty("name_fi")
    @Column(name = "name")
    private String name;
    
    @JsonProperty("name_sv")
    @Transient
    private String nameSv;
    
    @JsonProperty("city_fi")
    @Transient
    private String city;
    
    @JsonProperty("city_sv")
    @Transient
    private String citySv;

    @JsonProperty("wgs_coords")
    @Transient
    private Coordinates coordinates;

    @JsonProperty("timetable_link")
    @Transient
    private String timetableLink;
    
    @JsonProperty("address_fi")
    private String address;
    
    @JsonProperty("address_sv")
    @Transient
    private String addressSv;
    
    @JsonProperty("departures")
    @Transient
    private List<Departure> departures;
    
    @JsonProperty("lines")
    @Transient
    private List<LineInfo> lineInfos;
    
    @Transient
    private List<Line> lines;
    
    @JsonIgnore
    @ManyToMany(mappedBy ="favouriteStops")
    private List<SiteUser> favouritedBy;

    public Stop() {
    }

    public List<SiteUser> getFavouritedBy() {
        return favouritedBy;
    }

    public void setFavouritedBy(List<SiteUser> favouritedBy) {
        this.favouritedBy = favouritedBy;
    }
    
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @JsonDeserialize(using = CoordinateDeserializer.class)
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    public List<LineInfo> getLineinfos() {
        return lineInfos;
    }

    @JsonDeserialize(using = LineDeserializer.class)
    public void setLineinfos(List<LineInfo> lines) {
        this.lineInfos = lines;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    

    public String getCitySv() {
        return citySv;
    }

    public void setCitySv(String citySv) {
        this.citySv = citySv;
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

    @Override
    public int hashCode() {
        return this.code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stop other = (Stop) obj;
        if (this.code != other.code && (this.code == null || !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

}
