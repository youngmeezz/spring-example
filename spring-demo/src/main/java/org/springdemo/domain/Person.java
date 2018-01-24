package org.springdemo.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdemo.util.GsonUtil;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer personId;
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return GsonUtil.toString(this);
    }
}
