package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitaionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitaionName : beanDefinitaionNames) {
            Object bean = ac.getBean(beanDefinitaionName);
            System.out.println("beanDefinitaionName = " + beanDefinitaionName + " \n object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitaionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitaionName : beanDefinitaionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitaionName);

            //Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
            //Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitaionName);
                System.out.println("beanDefinitaionName = " + beanDefinitaionName + " \n object = " + bean);
            }

        }
    }
}
