package com.sample.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sample.domain.Member;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestSampleController {    
    @RequestMapping(value = "/api/members", method=RequestMethod.GET)
    public ResponseEntity<List<Member>> memberList() {
        log.debug("## [request member list]");
        Member m1 = new  Member("zaccoding","zac",20);
        Member m2 = new  Member("swagger","tester",17);
        return new ResponseEntity<>(Arrays.asList(m1,m2),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/member/{id}", method=RequestMethod.GET)
    public Member getMember(@PathVariable("id") String id) {
        log.debug("## [request find member] id : {}",id);
        return new  Member("zaccoding","zac",20);
    }
    
    @RequestMapping(value = "/api/member", method=RequestMethod.POST)
    public boolean saveMember(@RequestBody Member member) {
        log.debug("## [request save member] member : {}",member);
        return true;
    }    
    
    @RequestMapping(value = "/api/member", method=RequestMethod.PATCH)
    public ResponseEntity<Boolean> updateMember(@RequestBody Member member) {
        log.debug("## [request update member] member : {}",member);
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/member/{id}", method=RequestMethod.DELETE)
    public boolean deleteMember(@PathVariable("id") String id) {
        log.debug("## [request delete member] id : {}",id);
        return true;
    }
}
