package com.zzxx.travel.service;

import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;

public interface RouteService {
    PageBean<Route> findByPage(int cid,int currentPage,int pageSize);
    Route findOne(int rid);
}
