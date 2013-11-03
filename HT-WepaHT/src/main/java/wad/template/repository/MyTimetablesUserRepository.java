package wad.template.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wad.template.domain.User;

@Repository
public interface MyTimetablesUserRepository  extends CrudRepository<User, String> {
    User findOneByApikey(String apikey);
}
