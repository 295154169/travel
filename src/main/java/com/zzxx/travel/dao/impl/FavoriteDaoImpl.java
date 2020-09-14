package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.FavoriteDao;
import com.zzxx.travel.domain.Favorite;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询线路被收藏次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, rid);
    }

    /**
     * 查询用户有没有收藏指定线路
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用户收藏线路
     * @param rid
     * @param uid
     */
    @Override
    public void insert(int rid, int uid) {
        String sql = "insert into tab_favorite(rid, uid, date) values(?,?,?)";
        jdbcTemplate.update(sql, rid, uid, new Date());
    }
}
