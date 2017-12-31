package com.demo.mapper;

import com.demo.domain.Board;
import com.demo.runner.AbstractTestRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
public class BoardMapperTest extends AbstractTestRunner {
    @Autowired
    private BoardMapper boardMapper;

    @Test
    public void select() {
        List<Board> list = boardMapper.findAll();
        System.out.println("list size : " + list.size());
    }

}
