package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.RouteImgDao;
import com.zzxx.travel.domain.RouteImg;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据线路的rid, 查询对应的图片列表
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findListByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
