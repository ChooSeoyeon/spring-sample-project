package hello;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

public class HelloTest {
    @Test
    void test1() {
        // IoC 컨테이너 생성
        StaticApplicationContext context = new StaticApplicationContext();
        // Hello 클래스를 싱글톤 빈으로 컨테이너에 등록
        context.registerSingleton("hello", Hello.class);

        Hello hello = context.getBean("hello", Hello.class);
        assertThat(hello).isNotNull();
    }

    @Test
    void test2() {
        StaticApplicationContext context = new StaticApplicationContext();
        context.registerSingleton("hello1", Hello.class);

        Hello hello1 = context.getBean("hello1", Hello.class);
        assertThat(hello1).isNotNull();

        // 빈 메정보를 담은 오프젝트 생성
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        // 빈 프로퍼티 설정
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        // 빈 메타정보를 hello2 라는 이름으로 컨테이너에 등록
        context.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = context.getBean("hello2", Hello.class);
        assertThat(hello2).isNotNull();
        assertThat(hello2.sayHello()).isEqualTo("Hello Spring");

        assertThat(hello1).isNotSameAs(hello2);
        assertThat(context.getBeanFactory().getBeanDefinitionCount()).isEqualTo(2);
    }

}
