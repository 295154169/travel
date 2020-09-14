package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Favorite;

public interface FavoriteDao {
    int findCountByRid(int rid);

    Favorite findByRidAndUid(int rid, int uid);

    void insert(int rid, int uid);
}
