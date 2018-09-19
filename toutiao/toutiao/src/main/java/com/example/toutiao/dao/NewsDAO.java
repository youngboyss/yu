package com.example.toutiao.dao;

import com.example.toutiao.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsDAO {
    String TABLE_NAME="news";
    String INSERT_FILEDS=" title,link,image,like_count,comment_count,created_date,user_id ";
    String SELECT_FILEDS=" id," +INSERT_FILEDS;
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILEDS,") values(#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    List<News> selectByUserIdAndOffset(@Param("userId") int userid, @Param("offset") int offset, @Param("limit") int limit);

}
