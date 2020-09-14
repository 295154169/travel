package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.CategoryDao;
import com.zzxx.travel.dao.impl.CategoryDaoImpl;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import com.zzxx.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
//        Jedis jedis = JedisUtil.getJedis();
//        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
//        if (category == null || category.size() == 0){
//            // 查不到数据, 从数据库中查询并返回
//            List<Category> list = categoryDao.findAllCategory();
//            for (Category cate:list) {
//                jedis.zadd("category",cate.getCid(),cate.getCname());
//            }
//            return list;
//        }else {
//            //查到数据, 直接将数据封装返回
//            List<Category> categoryList = new ArrayList<>();
//            for (Tuple tuple:category) {
//                Category cate = new Category();
//                cate.setCid((int)tuple.getScore());
//                cate.setCname(tuple.getElement());
//                categoryList.add(cate);
//            }
//            return categoryList;
//        }

//        此方法不需要连接sshd
        List<Category> list = categoryDao.findAllCategory();
        return list;
    }
}
