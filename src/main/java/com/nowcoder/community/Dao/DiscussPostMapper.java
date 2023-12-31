package com.nowcoder.community.Dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);
    //@Param注解用于参数取别名，如果只有一个参数，并且在<if>里使用，就必须加别名
    int selectDiscussPostRows(@Param("userId") int userId); //查询表的行数 注解起别名字

    int insertDiscussPost(DiscussPost discussPost);
    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id,int commnetCount);

}
