package com.demo;

import com.demo.domain.Member;
import com.demo.domain.MemberRole;
import com.demo.mapper.MemberMapper;
import com.demo.runner.AbstractTestRunner;
import com.demo.service.MemberService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
public class SampleDatasTest extends AbstractTestRunner {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;
    List<Member> members;

    @Before
    public void setUp() {
        memberMapper.deleteAll();
        members = new ArrayList<>();

        Member m1 = new Member();
        m1.setLoginId("read");
        m1.setPassword(passwordEncoder.encode("1234"));
        m1.setName("readUser");
        m1.setRoles(createRole(m1.getLoginId(), "read"));
        members.add(m1);

        Member m2 = new Member();
        m2.setLoginId("write");
        m2.setPassword(passwordEncoder.encode("1234"));
        m2.setName("writeUser");
        m2.setRoles(createRole(m2.getLoginId(), "read", "write"));
        members.add(m2);

        Member m3 = new Member();
        m3.setLoginId("modify");
        m3.setPassword(passwordEncoder.encode("1234"));
        m3.setName("modifyUser");
        m3.setRoles(createRole(m3.getLoginId(), "read", "write", "modify"));
        members.add(m3);

        Member m4 = new Member();
        m4.setLoginId("remove");
        m4.setPassword(passwordEncoder.encode("1234"));
        m4.setName("removeUser");
        m4.setRoles(createRole(m4.getLoginId(), "read", "write", "modify", "remove"));
        members.add(m4);
    }

    @Test
    public void createMembers() {
        for(Member m : members) {
            memberService.save(m);
        }
    }

    @Test
    public void findOne() {
        String loginId = "write";
        Member m = memberMapper.findById(loginId);
        System.out.println(m);
    }


    private List<MemberRole> createRole(String loginId, String ... roleNames) {
        List<MemberRole> roles = new ArrayList<>(roleNames.length);

        for(String roleName : roleNames) {
            MemberRole role  = new MemberRole();
            role.setRoleName(roleName);
            role.setLoginId(loginId);
            roles.add(role);
        }

        return roles;
    }
}
