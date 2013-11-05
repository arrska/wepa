package wad.hsltimetables.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.hsltimetables.domain.Stop;
import wad.hsltimetables.domain.User;
import wad.hsltimetables.repository.UserRepository;

@Service
public class FavouriteStopService implements FavouriteService<Stop> {
    @Autowired
    UserRepository userRepo;
    
    @Override
    @Transactional(readOnly = false)
    public void favourite(User user, Stop stop) {
        user.getFavouriteStops().add(stop.getCode());
        userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void unfavourite(User user, Stop stop) {
        user.getFavouriteStops().remove(stop.getCode());
        userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isFavourite(User user, Stop stop) {
        if (user == null) return false;
        if (stop == null) return false;
        if (user.getFavouriteStops() == null) return false;
        return user.getFavouriteStops().contains(stop.getCode());
    }
    
}
