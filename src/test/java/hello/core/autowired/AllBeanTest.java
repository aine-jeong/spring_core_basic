package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }

        /*
        ### section7. 의존관계 자동 주입 - 조회한 빈이 모두 필요할 때

        1. DiscountService는 Map으로 모든 'DiscountPolicy'를 주입받는다. 이때 'fixDiscountPolicy', 'rateDiscountPolicy'가 주입된다.
        2. 'discount()' 메서드는 discountCode로 'fixDiscountPolicy'가 넘어오면 Map에서 'fisDiscountPolicy' 스프링 빈을 찾아서 실행한다.
        3. 'Map<String, DiscountPolicy>': map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로 'DiscountPolicy' 타입으로 조회한 모든 스프링 빈을 담아준다.
        4. 'List<DiscountPolicy>': 'DiscountPolicy' 타입으로 조회한 모든 스프링 빈을 담아준다.
        5. 만약 해당하는 타입의 스프링 빈이 없으면, 빈 컬렉션이나 Map을 주입한다.

         */

        /*

        ### section7. 의존관계 자동 주입 - 자동, 수동의 실무 운영 기준

        * 편리한 자동 기능을 기본으로 사용한다.
        * 수동 빈 등록이 필요한 경우는?

            1. 애플리케이션에 광범위하게 영향을 미치는 기술 지원 객체는 수동 빈으로 등록해서 설정 정보에 바로 나타나게 하는 것이 유지보수하기 좋다.
                애플리케이션은 크게 업무 로직과 기술 지원 로직으로 나눌 수 있다.
                - 업무 로직 빈: 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층의 로직을 처리하는 레파지토리등이 모두 업무 로직이다. 보통 비즈니스 요구사항을 개발할 떄 추가된거나 변경된다.
                - 기술 지원 빈: 기술적인 문제나 공통 관심사(AOP)를 처리할 떄 주로 사용된다. 데이터베이스 연결이나, 공통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술들이다.

                - 업무 로직은 숫자도 매우 많고, 한번 개발해야 하면 컨트롤러, 서비스, 리포지토리처럼 어느 정도 유사한 패턴이 있다. 이런 경우 자동 기능을 적극 사용하는 것이 좋다.
                    보통 문제가 발생해도 어떤 곳에서 문제가 발생했는지 명확하게 파악하기 쉽다.
                - 기술 지원 로직은 업무 로직과 비교해서 그 수가 매우 적고, 보통 애플리케이션 전반에 걸쳐서 광범위하게 영향을 미친다.
                    그리고 업무 로직은 문제가 발생했을 때 어디가 문제인지 명확하게 잘 드러나지만, 기술 지원 로직은 적용이 잘 되고 있는지 아닌지 조차 파악하기 어려운 경우가 많다.
                    그래서 이런 기술 지원 로직들은 가급적 수동 빈 등록을 사용해서 명확하게 드러내는 것이 좋다.

            2. 비즈니스 로직 중에서 다형성을 적극 활용할 때
                - 스프링 부트가 제공하는 것이 아닌 자신이 직접 기술 지원 객체를 스프링 빈으로 등록한다면 수동으로 등록하여 명확하게 드러내는 것이 좋다.
                - 만약 수동 빈으로 등록하지 않더라도 특정 패키지에 같이 묶어 두는게 좋다.


         */
    }
}
