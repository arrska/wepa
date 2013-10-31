package wad.template.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class SiteUser implements Serializable {
    @Id
    @Column(name = "username")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "apikey")
    private String apikey;
    
    @ManyToMany
    @JoinTable(name = "favourite_stops",
        joinColumns = @JoinColumn(name = "username"),
        inverseJoinColumns=@JoinColumn(name="stopcode"))
    private List<Stop> favouriteStops;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Stop> getFavouriteStops() {
        return favouriteStops;
    }
    
    public List<Integer> getFavouriteStopcodes() {
        if (favouriteStops == null) return null;
        List<Integer> codes = new ArrayList<Integer>();
        
        for (Stop stop : favouriteStops) {
            codes.add(stop.getCode());
        }
        return codes;
    }

    public void setFavouriteStops(List<Stop> favouriteStops) {
        this.favouriteStops = favouriteStops;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
    
}
