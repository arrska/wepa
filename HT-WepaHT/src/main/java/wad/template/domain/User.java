package wad.template.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User implements Serializable {
    @Id
    Long Id;
    String name;
    String passwordHash;
    
    /*@ManyToMany
    List<Stop> favouriteStops;*/
}
