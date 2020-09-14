package com.zzxx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.FavoriteService;
import com.zzxx.travel.service.RouteService;
import com.zzxx.travel.service.impl.FavoriteServiceImpl;
import com.zzxx.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    public void queryPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String _cid = request.getParameter("cid");
        int cid = Integer.parseInt(_cid);
        String _currentPage = request.getParameter("currentPage");
        int currentPage = 1;
        if (_currentPage != null && _currentPage.length() > 0){
            currentPage = Integer.parseInt(_currentPage);
        }
        String _pageSize = request.getParameter("pageSize");
        int pageSize = 8;
        if (_pageSize != null && _pageSize.length() > 0) {
            pageSize = Integer.parseInt(_pageSize);
        }
        PageBean<Route> page = routeService.findByPage(cid, currentPage, pageSize);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), page);
    }

    public void detail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.接收参数rid
        int rid = Integer.parseInt(request.getParameter("rid"));
        // 2.调用service获得route对象
        Route route = routeService.findOne(rid);
        // 3.序列化返回
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), route);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.判断用户有没有登录
        Object user = request.getSession().getAttribute("loginUser");
        ResultInfo info = new ResultInfo();
        if (user == null) {
            info.setFlag(false);
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(), info);
            return;
        }
        // 2.如果登录了, 在判断该线路有没有被用户收藏
        int rid = Integer.parseInt(request.getParameter("rid"));
        int uid = ((User) user).getUid();
        if (favoriteService.isFavorite(rid, uid)) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    public void favorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int rid = Integer.parseInt(request.getParameter("rid"));
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            // 没有登录
            return;
        }
        favoriteService.addFavorite(rid, user.getUid());
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), true);
    }
}
