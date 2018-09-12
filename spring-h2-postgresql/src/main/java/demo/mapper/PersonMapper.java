package demo.mapper;

import demo.model.Person;
import java.util.List;

/**
 * @author zacconding
 * @Date 2018-07-04
 * @GitHub : https://github.com/zacscoding
 */
public interface PersonMapper {

    List<Person> findAll();
}
