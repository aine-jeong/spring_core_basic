package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    // excludeFilters로 Configuration.class를 제외하여 기존 예제인 AppConfig가 자동으로 등록되는 것을 방지함
    // ComponentScan은 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
    // 각 구현체에 @Component 애노테이션을 붙이고, 의존관계 주입이 필요한 곳에 @Autowired 애노테이션을 붙였음!
    //   -> MemberServiceImpl, RateDiscountPolicy, OrderServiceImpl

    // 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
    // 생성자에 @Autowired를 지정하면 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
    // 이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
}
