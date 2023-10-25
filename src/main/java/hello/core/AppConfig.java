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

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    private DiscountPolicy discountPolicy() {
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
     */


}
