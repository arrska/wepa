package wad.hsltimetables.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wad.hsltimetables.domain.User;

@Repository
public interface MyTimetablesUserRepository  extends CrudRepository<User, String> {
    User findOneByApikey(String apikey);
}
