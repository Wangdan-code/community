package com.nowcoder.community.Dao;

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate") //加注解 让其被访问到
public class AlphaDaoHibernatelmpl implements AlphaDao {
    @Override
    public String select(){
        return "Hibernate";
    }
}
