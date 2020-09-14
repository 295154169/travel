package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findCount(int cid);
    List<Route> findByPage(int cid,int start,int pageSize);
    Route findById(int rid);
}
