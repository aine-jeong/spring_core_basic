package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }

    /*
    Unsatisfied dependency expressed through constructor parameter 1; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'hello.core.discount.DiscountPolicy' available: expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy
    RateDiscountPolicy, FixDiscountPolicy 두 클래스 모두 @Component 설정을 해두면 자동 의존관계 주입시 에러 발생. (조회 빈이 2개 이상)

    이때 하위 타옵으로 지정할 수도 있지만, 하위 타입으로 지정하는 것은 DIP를 위배하고 유연성이 떨어진다.
    그리고 이름만 다르고 완전히 똑같은 타입의 스프링 빈이 2개 있을 때 해결이 안된다.
    스프링 빈을 수동 등록해서 문제를 해결해도 되지만, 의존 관계 자동 주입에서 해결하는 여러 방법이 있다.
    [Autowired 필드 명, @Qualifier, @Primary]

    1. Autowired 필드 명
        : @Autowired는 타입 매칭을 시도하고, 이때 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭한다.
        -> 필드 명을 빈 이름으로 변경한다.
        -> public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy)

    2. @Qualifier: 추가 구분자를 붙여주기.
        -> @Qualifier("mainDiscountPolicy")
        -> public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy)
        -> @Qualifier로 주입할 때 해당 이름을 못찾으면 mainDiscountPolicy라는 이름의 스프링 빈을 추가로 찾는다.
        -> 그러나 @Qualifier는 @Qualifier를 찾는 용도로만 사용하는게 명확하고 좋다.

    3. @Primary
        : 우선순위를 정하는 방법이다. @Autowired시 여러 빈이 매칭되면 @Primary가 우선권을 가진다.
        : 구현체에 @Primary 어노테이션을 붙여주면 된다.
        @Primary
        public class RateDiscountPolicy implements DiscountPolicy


    =>
        코드에서 자주 사용하는 메인 테이터베이스의 커넥션을 획득하는 스프링 빈이 있고, 코드에서 특별한 기능으로 가끔 사용하는 서브 데이터베이스의 커넥션을 획득하는 스프링 빈이 있다고 생각해보자.
        메인 데이터베이스의 커넥션을 획득하는 스프링 빈은 '@Primary'를 적용해서 조회하는 곳에서 '@Qualifier' 지정 없이 편리하게 조회하고,
        서브 데이터베이스 커넥션 빈을 획득할 때는 '@Qualifier'를 지정해서 명시적으로 획득하는 방식으로 사용하면 코드를 깔끔하게 유지할 수 있다.
        물론 이때 메인 데이터베이스의 스프링 빈을 등록할 때 '@Qualifier'를 지정해 주는 것은 상관 없다.

        * @Primary 보다는 @Qualifier가 우선권이 높다.
     */
}
