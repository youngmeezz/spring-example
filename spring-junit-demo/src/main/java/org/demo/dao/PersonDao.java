package org.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.model.Person;

/**
 * @author zacconding
 * @Date 2018-09-12
 * @GitHub : https://github.com/zacscoding
 */
public interface PersonDao {

    @Insert("INSERT INTO PERSON (id, name, age) VALUES(#{id}, #{name}, #{age})")
    int savePerson(Person person);

    @Update("UPDATE PERSON SET name = #{name}, age = #{age} WHERE id = #{id}")
    int updatePerson(Person person);

    @Delete("DELETE FROM PERSON WHERE id = #{id}")
    int deletePerson(String id);

    @Select("SELECT * FROM PERSON WHERE id = #{id}")
    Person getPersonById(String id);
}