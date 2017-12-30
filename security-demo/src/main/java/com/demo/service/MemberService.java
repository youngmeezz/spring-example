package com.demo.service;

import com.demo.domain.Member;
import com.demo.domain.MemberRole;
import com.demo.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Transactional
    public void save(Member member) {
        memberMapper.save(member);
        List<String> roleNames = new ArrayList<>(member.getRoles().size());
        for(MemberRole role : member.getRoles()) {
            roleNames.add(role.getRoleName());
        }

        memberMapper.saveRoles(roleNames, member.getLoginId());
    }
}
