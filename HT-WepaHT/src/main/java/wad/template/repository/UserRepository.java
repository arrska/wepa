

package wad.template.repository;

import org.springframework.data.repository.CrudRepository;
import wad.template.domain.SiteUser;

public interface UserRepository extends CrudRepository<SiteUser, String>{
    SiteUser findOneByApikey(String apikey);
}
