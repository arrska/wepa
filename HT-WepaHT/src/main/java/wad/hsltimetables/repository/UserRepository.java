

package wad.hsltimetables.repository;

import org.springframework.data.repository.CrudRepository;
import wad.hsltimetables.domain.User;

public interface UserRepository extends CrudRepository<User, String>{
    User findOneByApikey(String apikey);
}
