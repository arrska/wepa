package wad.template.service;

import wad.template.domain.User;

public interface FavouriteService<T> {
    public void favourite(User user, T favouritable);
    public void unfavourite(User user, T favouritable);
    public Boolean isFavourite(User user, T favouritable);
}
