package springbook.learningtest.jdk.proxy;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * @author zacconding
 * @Date 2018-03-14
 * @GitHub : https://github.com/zacscoding
 */
public class DynamicProxyTest {

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        // 타깃 설정
        pfBean.setTarget(new HelloTarget());
        // 부가 기능을 담은 어드바이스 추가
        pfBean.addAdvice(new UppercaseAdvice());
        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("zaccoding"), is("HELLO ZACCODING"));
        assertThat(proxiedHello.sayHi("zaccoding"), is("HI ZACCODING"));
        assertThat(proxiedHello.sayThankYou("zaccoding"), is("THANK YOU ZACCODING"));
    }

    @Test
    public void pointcutAdvisor() {
        /* create proxy factory bean */
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        // 메소드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트컷 생성
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        /* use proxy */
        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("zaccoding"), is("HELLO ZACCODING"));
        assertThat(proxiedHello.sayHi("zaccoding"), is("HI ZACCODING"));
        assertThat(proxiedHello.sayThankYou("zaccoding"), is("Thank You zaccoding"));
    }

    static class HelloUppercase implements Hello {

        Hello hello;

        public HelloUppercase(Hello hello) {
            this.hello = hello;
        }

        public String sayHello(String name) {
            return hello.sayHello(name).toUpperCase();
        }

        public String sayHi(String name) {
            return hello.sayHi(name).toUpperCase();
        }

        public String sayThankYou(String name) {
            return hello.sayThankYou(name).toUpperCase();
        }

    }

    static class UppercaseHandler implements InvocationHandler {

        Object target;

        private UppercaseHandler(Object target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object ret = method.invoke(target, args);
            if (ret instanceof String && method.getName().startsWith("say")) {
                return ((String) ret).toUpperCase();
            } else {
                return ret;
            }
        }
    }

    static class UppercaseAdvice implements MethodInterceptor {

        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }


    static interface Hello {

        String sayHello(String name);

        String sayHi(String name);

        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello {

        public String sayHello(String name) {
            return "Hello " + name;
        }

        public String sayHi(String name) {
            return "Hi " + name;
        }

        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
    }
}
