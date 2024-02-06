package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }


    /**
     *  1.
     *     appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl을 생성하면서 생성자로 전달한다.
     *     클라이언트인 memberServiceImpl입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서
     *     DI(Dependency Injection) 즉 의존관계 주입, 의존성 주입이라고 한다.
     *
     *     설계 변경으로 OrderServiceImpl은 FixDiscountPolicy를 의존하지 않는다. 단지 'DiscountPolicy'인터페이스에만 의존한다.
     *     OrderServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
     *     OrderServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig)에서만 결정한다.
     *     OrderServiceImpl은 이제부터 실행에만 집중하면 된다.
     *
     *
     *  2.
     *     제어의 역전(IoC, Inversion of Control)
     *     프로그램 제어 흐름에 대한 권한은 모두 AppConfig가 가지고 있다.
     *     프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC)이라 한다.
     *
     *  3.
     *     프레임워크 vs 라이브러리
     *     프레임워크가 내가 작성한 코드를 제어하고, 대신 실행하면 프레임워크
     *     반면 내가 작성한 코드가 직접 제어의 흐름을 담당하면 라이브러리
     *
     *  4.
     *     '정적인 클래스 의존관계' / 클래스 다이어그램
     *     - 클래스가 사용하는 import코드만 보고 의존관계를 쉽게 판단할 수 있다.
     *     - 정적인 의존관계는 애플리케이션을 실행하지 않아도 분석할수 있다.
     *     - but, 이러한 의존관계만으로는 실제 어떤 객체가 'OrderServiceImpl'에 주입될 지 알 수 없다.
     *
     *     '동적인 객체 인스턴스 의존 관계' / 객체 다이어그램
     *     - 애플리케이션 "실행 시점(런타임)"에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달에서
     *       클라이언트와 서버의 실제 의존관계가 연결되는 것을 "의존관계 주입"이라고 한다.
     *     - 객체 인스턴스를 생성하고, 그 참조값을 전달해서 연결한다.
     *     - 의존관계 주입을 사용하면 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.
     *
     *  5.
     *     'IoC 컨테이너, DI 컨테이너'
     *     - AppConfig처럼 객체를 생성하고 관리하면서 의존관계를 연결해주는 것을 IoC컨테이너 또는 DI컨테이너 라고 한다.
     *     - 어셈블러, 오브젝트 팩토리 등으로 불리기도 한다.
     *
     *
     */


}
