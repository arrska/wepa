package wad.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.template.domain.SiteUser;
import wad.template.domain.Stop;
import wad.template.repository.UserRepository;

@Service
public class FavouriteStopService implements FavouriteService<Stop> {
    @Autowired
    UserRepository userRepo;
    
    @Override
    @Transactional(readOnly = false)
    public void favourite(SiteUser user, Stop stop) {
        user.getFavouriteStops().add(stop);
        userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void unfavourite(SiteUser user, Stop stop) {
        user.getFavouriteStops().remove(stop);
        userRepo.save(user);
    }

    @Override
    public Boolean isFavourite(SiteUser user, Stop stop) {
        if (user == null) return false;
        if (stop == null) return false;
        if (user.getFavouriteStopcodes() == null) return false;
        return user.getFavouriteStops().contains(stop);
    }
    
}
