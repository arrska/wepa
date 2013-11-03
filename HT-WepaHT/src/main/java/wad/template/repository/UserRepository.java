

package wad.template.repository;

import org.springframework.data.repository.CrudRepository;
import wad.template.domain.User;

public interface UserRepository extends CrudRepository<User, String>{
    User findOneByApikey(String apikey);
}
