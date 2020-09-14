package com.zzxx.travel.service;

public interface FavoriteService {
    void addFavorite(int rid, int uid);

    boolean isFavorite(int rid,int uid);
}
