package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    /*
        excludeFilters로 Configuration.class를 제외하여 기존 예제인 AppConfig가 자동으로 등록되는 것을 방지함
        ComponentScan은 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
        각 구현체에 @Component 애노테이션을 붙이고, 의존관계 주입이 필요한 곳에 @Autowired 애노테이션을 붙였음!
          -> MemberServiceImpl, RateDiscountPolicy, OrderServiceImpl

        이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
        생성자에 @Autowired를 지정하면 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
        이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
     */


    /*
        옵션
        basePackages : 탐색할 패키지의 시작 위치를 지정할 수 있다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
            basePackages = {"hello.core", "hello.service"} -> 시작위치 여러개 설정 가능
        basePackageClasses : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
        만약 basePackage를 지정하지 않으면 @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        (권장) 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것!

        ComponentScan 기본 대상
            - @Component
            - @Controller : 스프링 MVC 컨트롤러에서 사용 -> 스프링MVC 컨트롤러로 인식
            - @Service : 스프링 비즈니스 로직에서 사용 -> 특별한 처리를 하지는 않지만, 개발자들이 핵심 비즈니스 로직이 여기에 있겠구나 라고 비즈니스 계층을 인식하는데 도움이 된다.
            - @Repository : 스프링 데이터 접근 계층에서 사용 -> 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
            - @Configuration : 스프링 설정 정보에서 사용 -> 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 한다.
     */

    /*
        수동 빈 등록과 자동 빈 등록이 같은 이름으로 이뤄질 경우 수동 빈 등록이 우선된다. (수동 빈이 자동 빈을 오버라이딩 해버린다.)
         -> But, 정말 잡기 어려운 버그가 만들어진다.... 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꿨다.
     */

    /*

    ### 다양한 의존관계 주입 방법

    1. 생성자 주입
        - 생성자 호출 시점에 딱 1번만 호출되는 것이 보장된다.
        - '불변, 필수' 의존관계에 사용한다.
        - ** 생성자가 "딱 1개만 있으면" @Autowired를 생략해도 자동 주입 된다. **

    2. 수정자 주입
        - setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존 관계를 주입하는 방법이다.
        - '선택, 변경' 가능성이 있는 의존관계에 사용한다.
        - 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법이다.

    3. 필드 주입 (사용x)
        - 필드에 바로 주입하는 방법이다.
        - 코드가 간결하지만 외부에서 변경이 불가능해서 테스트하기 힘들다는 치명적인 단점이 있다.
        - DI 프레임워크가 없으면 아무것도 할 수 없다.
        - 애플리케이션의 실제 코드와 관계 없는 테스트코드나, 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용한다.

    4. 일반 메서드 주입
        - 일반 메서드를 통해서 주입받을 수 있다.
        - 한번에 여러 필드를 주입 받을 수 있다.
        - 일반적으로 잘 사용하지 않는다.

     */

}
