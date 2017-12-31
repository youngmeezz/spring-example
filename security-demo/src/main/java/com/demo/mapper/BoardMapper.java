package com.demo.mapper;

import com.demo.domain.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
public interface BoardMapper {
    @Select("select * from tbl_boards")
    public List<Board> findAll();

    @Select("select * from tbl_boards where id = #{id}")
    public Board findById(@Param("id") Long id);


    @Insert("insert into tbl_boards (title, content, writer) values(" +
            "#{title}, #{content}, #{writer})")
    public int save(Board board);

    @Update("update tbl_boards set title = #{title}, content = #{content}, mod_date = #{modDate} where id = #{id}")
    public int update(Board board);

    @Delete("delete from tbl_boards where id = #{id}")
    public int delete(@Param("id") Long id);
}