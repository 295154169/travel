package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findCount(int cid) {
        String sql = "select count(*) from tab_route where cid = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cid);
        return count;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize) {
        String sql = "select * from tab_route where cid = ? limit ?,?";
        List<Route> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class),cid, start, pageSize);
        return list;
    }

    @Override
    public Route findById(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }
}
