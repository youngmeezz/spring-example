package com.demo.security;

import com.demo.domain.Member;
import com.demo.mapper.MemberMapper;
import com.demo.security.domain.SecurityMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailServiceImpl implements UserDetailsService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberMapper.findById(username);

        if(member == null) {
            return null;
        }

        System.out.println(member.toString());

        return new SecurityMember(member);
    }
}