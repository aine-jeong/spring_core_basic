package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    /*
        프레임워크 없이 순수한 자바 코드를 단위 테스트 하는 경우에 수정자 의존관계인 경우
        'Autowired'가 프레임워크 안에서 동작할 때는 의존관계가 없으면 오류가 발생하지만,
        지금은 프레임워크 없이 순수한 자바 코드로만 단위 테스트를 수행하고 있다.

        ==> "" 생성자 주입을 선택해라! ""
         -> "불변", "누락", "final 키워드"
         - 생성자 주입 방식을 선택하면 프레임워크에 의존하지 않고 순수한 자바 언어의 특징을 잘 살릴 수 있다.
         - 기본적으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다.
         - 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
          => 항상 생성자 주입을 선택해라! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지 않는게 좋다.
     */

    @Test
    void createOrder() {

        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }

}