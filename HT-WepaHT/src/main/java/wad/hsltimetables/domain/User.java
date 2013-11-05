package wad.hsltimetables.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "username")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "apikey")
    private String apikey;
    
    @ElementCollection
    @CollectionTable(name = "favouritestops", joinColumns = @JoinColumn(name = "username"))
    @Column(name="stopcode")
    private List<Integer> favouriteStops;

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

    public List<Integer> getFavouriteStops() {
        return favouriteStops;
    }

    public void setFavouriteStops(List<Integer> favouriteStops) {
        this.favouriteStops = favouriteStops;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
    
}
