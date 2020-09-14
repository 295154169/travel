package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.SellerDao;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.Seller;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据sid查询Seller对象
     * @param sid
     * @return
     */
    @Override
    public Seller findById(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
    }
}
