package reflection;

import com.demo.domain.Person;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author zacconding
 * @Date 2017-12-21
 * @GitHub : https://github.com/zacscoding
 */

public class FieldAccessTest {
    @Test
    public void accessField() {
        Person p = new Person();
        p.setName("person");
        p.setSeq(Integer.valueOf(1));
        p.setRegDate(new Date());

        System.out.println("## check field value");
        Class<?> clazz = p.getClass();
        for(Field f : clazz.getDeclaredFields()) {
            //f.setAccessible(true);
            try {
                f.setAccessible(true);
                System.out.println(f.getName() + " :: " + String.valueOf(f.get(p)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println("## check accessible");
        for(Field f : p.getClass().getDeclaredFields()) {
            System.out.println(f.getName() + " :: "+ f.isAccessible());
        }
    }
}