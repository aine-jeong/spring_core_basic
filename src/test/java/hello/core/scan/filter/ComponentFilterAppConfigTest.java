package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();


        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));
        /*
        @ComponentScan에서 includeFilters에 MyIncludeComponent를 추가하여 BeanA가 스프링 빈에 등록되었고,
                          excludeFilters에 MyExcludeComponent를 추가하여 BeanB가 스프링 빈에 등록되지 않았다.


        FilterType 옵션
            - ANNOTATION: 가본값, 애노테이션을 인식해서 동작한다.
            - ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다.
            - ASPECTJ: AspectJ 패턴을 사용한다.
            - REGEX: 정규 표현식
            - CUSTOM: 'TypeFilter'라는 인터페이스를 구현해서 처리한다.
         */
    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {

    }
}
