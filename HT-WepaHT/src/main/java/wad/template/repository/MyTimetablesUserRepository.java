package wad.template.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wad.template.domain.SiteUser;

@Repository
public interface MyTimetablesUserRepository  extends CrudRepository<SiteUser, String> {
    
}
