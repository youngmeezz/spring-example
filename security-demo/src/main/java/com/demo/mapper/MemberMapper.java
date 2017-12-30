package com.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.demo.domain.Member;

import java.util.List;

public interface MemberMapper {
    @Insert("insert into tbl_members (login_id, name, password) " +
            "values(#{loginId}, #{name}, #{password})")
    public int save(Member member);

    public void saveRoles(@Param("roleNames") List<String> roles, @Param("loginId") String loginId);

    public Member findById(@Param("loginId") String loginId);

    @Delete("delete from tbl_members")
    public void deleteAll();
}