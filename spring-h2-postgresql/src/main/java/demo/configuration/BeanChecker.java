package demo.configuration;

import java.util.StringTokenizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zacconding
 * @Date 2018-07-04
 * @GitHub : https://github.com/zacscoding
 */
@Component
public class BeanChecker implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext ctx = contextRefreshedEvent.getApplicationContext();
        int i = 0;
        System.out.println("============================================================================================================================");
        System.out.println(" Display beans");
        System.out.println("============================================================================================================================");
        for (String beanName : ctx.getBeanDefinitionNames()) {
            beanName = getShotName(beanName);
            if (beanName.length() > 50 && (i % 3) != 1) {
                System.out.println(beanName);
            } else {
                System.out.print(String.format("%-50s\t", beanName));
                if (i % 3 == 0) {
                    System.out.println();
                }
            }
            i++;
        }
        System.out.println("\n============================================================================================================================");
    }

    private String getShotName(String name) {
        int dotIdx = name.indexOf('.');
        if (dotIdx < 0) {
            return name;
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(name, ".");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (st.hasMoreElements()) {
                sb.append(token.charAt(0)).append('.');
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }
}