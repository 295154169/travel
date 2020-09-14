package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.FavoriteDao;
import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.dao.RouteImgDao;
import com.zzxx.travel.dao.SellerDao;
import com.zzxx.travel.dao.impl.FavoriteDaoImpl;
import com.zzxx.travel.dao.impl.RouteDaoImpl;
import com.zzxx.travel.dao.impl.RouteImgDaoImpl;
import com.zzxx.travel.dao.impl.SellerDaoImpl;
import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.RouteImg;
import com.zzxx.travel.domain.Seller;
import com.zzxx.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> findByPage(int cid, int currentPage, int pageSize) {
        PageBean<Route> pb = new PageBean<>();
        int totalCount = routeDao.findCount(cid);
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize);
        int totalPage = (totalCount + pageSize - 1) / pageSize ;
        pb.setCurrentPage(currentPage);
        pb.setList(list);
        pb.setPageSize(pageSize);;
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public Route findOne(int rid) {
        // 1.调用RouteDao 根据 rid查询Route对象
        Route route = routeDao.findById(rid);
        // 2.调用SellerDao 根据 sid 查询Seller对象
        Seller seller = sellerDao.findById(route.getSid());
        // 3.调用RouteImgDao 根据 rid 查询 图片列表
        List<RouteImg> list = routeImgDao.findListByRid(rid);
        // 4.属性注入
        route.setSeller(seller);
        route.setRouteImgList(list);

        int count = favoriteDao.findCountByRid(route.getRid());
        // 6.将count注入到route对象中
        route.setCount(count);
        // 5.返回route对象
        return route;
    }
}
