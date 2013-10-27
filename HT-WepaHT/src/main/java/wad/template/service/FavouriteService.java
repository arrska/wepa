package wad.template.service;

import wad.template.domain.SiteUser;

public interface FavouriteService<T> {
    public void favourite(SiteUser user, T favouritable);
    public void unfavourite(SiteUser user, T favouritable);
    public Boolean isFavourite(SiteUser user, T favouritable);
}
