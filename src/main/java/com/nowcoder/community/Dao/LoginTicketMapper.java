package com.nowcoder.community.Dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {
    @Insert({
            "insert into login_ticket (user_id,ticket,status,expired) ",
            "values (#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertLoginTicket(LoginTicket loginTicket);
    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket = #{ticket}"
    })
    //Ticket为唯一标识
    LoginTicket selectByTicket(String ticket);
    @Update({
            "update login_ticket set status=#{status} where ticket = #{ticket}"
    })
    //修改状态
    int updateStatus(String ticket,int status);

}
