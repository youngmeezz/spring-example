package com.demo.mapper;

import org.apache.ibatis.annotations.Param;

import com.demo.domain.Member;

public interface MemberMapper {
    
    public int save(@Param("member") Member member);
    
    
    
    
}
