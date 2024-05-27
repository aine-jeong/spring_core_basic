package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);
        //memberService -> memberRepository1 = hello.core.member.MemoryMemberRepository@518caac3
        //orderService -> memberRepository2 = hello.core.member.MemoryMemberRepository@518caac3
        //memberRepository = hello.core.member.MemoryMemberRepository@518caac3

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        /*
         각각 new로 생성되고 있는 것 처럼 보이지만 모두 같은 인스턴스가 조회되고 있다.

         각각 call할 때 로그를 찍어보면, new로 생성할 때 마다 매번 호출되지 않는 것을 확인할 수 있다.
         */

    }
    
    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
        //bean.getClass() = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$e9b74e74

        /*
        순수한 클래스라면 다음과 같이 출력되어야 한다.
        'class hello.core.AppConfig'

        but, 위와 같이 출력되는 이유는 이것이 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서
        AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.
            ==> so, 싱글톤이 보장되는 것


         */
    }
}
