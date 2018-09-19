package com.example.toutiao.dao;

import com.example.toutiao.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {

    String TABLE_NAME="user";
    String INSERT_FILEDS=" name,password,salt,head_url ";
    String SELECT_FILEDS=" id,name,password,salt,head_url ";
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILEDS,") values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME," where id=#{id}" })
    User selectById(int id);

    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME," where name=#{Name}" })
    User selectByName(String Name);
    @Update({"update ",TABLE_NAME," set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME," where id=#{id}"})
    void delteById(int id);

}

