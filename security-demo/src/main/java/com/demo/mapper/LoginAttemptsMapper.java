package com.demo.mapper;

import com.demo.domain.LoginAttempts;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zacconding
 * @Date 2018-01-02
 * @GitHub : https://github.com/zacscoding
 */
public interface LoginAttemptsMapper {

    @Insert("insert into login_attempts (ip, last_modified) values(#{ip}, now())")
    public int save(@Param("ip") String ip);

    @Select("select * from login_attempts where ip = #{ip}")
    public LoginAttempts findByIp(@Param("ip") String ip);

    @Update("update login_attempts set attempts = attempts + 1, last_modified=now() where ip = #{ip}")
    public int updateFailAttempts(@Param("ip") String ip);

    @Update("update login_attempts set attempts=0, last_modified = 0 where ip = #{ip}")
    public int resetFailAttempts(String ip);

    @Delete("delete from login_attempts")
    public int deleteAll();
}
