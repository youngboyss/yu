package com.example.toutiao.dao;

import com.example.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME="login_ticket";
    String INSERT_FILED=" user_id,expired,status,ticket ";
    String SELECT_FILED=" id,"+INSERT_FILED;

    @Insert({" insert into ",TABLE_NAME,"(",INSERT_FILED,") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

   @Select({"select ",SELECT_FILED," from ",TABLE_NAME," where ticket=#{ticket}"})
   LoginTicket selectByTicket(String Ticket);

   @Update({"update ",TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket,@Param("status") int status);


}
