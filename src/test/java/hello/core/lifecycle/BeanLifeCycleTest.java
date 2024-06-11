package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

        /*
        생성자 호출, url = null
        connect: null
        call: null / message = 초기화 연결 메시지

        객체를 생성하는 단계에는 url이 없고, 객체를 생성한 다음에 외부에서 수정자 주입을 통해서 'setUrl()'이 호출되어야 url이 존재하게 된다.

        "객체 생성 -> 의존관계 주입"

        스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해 초기화 시점을 알려주는 다양한 기능을 제공한다.
        또한 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.

        ### 스프링 빈의 이벤트 라이프사이클
        "스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료"
        - 초기화 콜백: 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
        - 소멸전 콜백: 빈이 소멸되기 적전에 호출

        - (tip) 객체의 생성과 초기화를 분리하자
            : 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다.
                반면 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행한다.
                따라서 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과 초기화하는 부분을 명확하게 나누는 것이 유지보수 관점에서 좋다.
                물론 초기화 작업이 내부 값들만 약간 변경하는 정도로 단순한 경우에는 생성자에서 한번에 다 처리하는게 더 나을 수도 있다.
         */

        /*
        1. implements InitializingBean, DisposableBean
        2. @Bean(initMethod = "init", destroyMethod = "close")
            : 설정 정보 사용
            - 스프링 빈이 스프링 코드에 의존하지 않는다.
            - 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
            - destroyMethod 속성은 'close' or 'shutdown' 라는 이름의 메서드를 자동으로 호출해준다. (디폴트가 'inferred')
            - 만약 추론 기능을 이용하고싶지 않다면 destroyMethod="" 로 지정해주면 된다.
        3. "추천" @PostConstruct, @PreDestroy
            - 최신 스프링에서 가장 권장하는 방법
            - 유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것. 외부 라이브러리를 초기화, 종료해야 한다면 @Bean 의 기능을 이용하자.

         */
    }

    @Configuration
    static class LifeCycleConfig {
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
