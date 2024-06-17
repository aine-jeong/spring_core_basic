package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

        /*
        여러 빈에서 같은 프로토타입 빈을 주입받으면, 주입받는 시점에 각각 새로운 프로토타입 빈이 생성된다.
         */
    }

    @Scope("singleton")
    static class ClientBean {
        //prototypeBean이 생성시점에 주입된다.
//        private final PrototypeBean prototypeBean;
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        /*
            ### ObjectFactory, ObjectProvider
            - 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공하는 것
            - 과거에 ObjectFactory가 있었고 여기에 편의 기능을 추가해서 ObjectProvider가 만들어졌다.

            - prototypeBeanProvider.getObject() 를 통해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인할 수 있다.
            - 'ObjectProvider'의 'getObject()'를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다. (DL)
            - 스프링이 제공하는 기능을 사용하지만, 기능이 단순하므로 단위테스트를 만들거나 mock코드를 만들기는 훨씬 쉬워진다.

            * 특징
                - ObjectFactory: 기능이 단순, 별도의 라이브러리 필요 없음, 스프링 의존
                - ObjectProvider: ObjectFactory 상속, 옵션/스트림 처리 등 편의 기능이 많고, 별도의 라이브러리 필요 없음, 스프링 의존


         */

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        /*
            ### JSR-330 Provider
            - 'javax.inject.Provider' / JSR-330 자바 표준 (라이브러리 추가 필요)
            - 'provider.get()'을 통해서 항상 새로운 프로토타입 빈이 생성된다.
            - 'provider'의 'get()'을 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다. (DL)
            - 자바 표준이고, 기능이 단순하므로 단위테스트를 만들거나 mock코드를 만들기는 훨씬 쉬워진다.

            * 특징
                - 'get()' 메서드 하나로 기능이 매우 단순하다.
                - 별도의 라이브러리가 필요하다.
                - 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있다.
         */

        /*

        "정리"
        : 매번 사용할 때 마다 의존관계 주입이 완료된 새로운 객체가 필요하면 프로토타입 빈을 사용하면 된다.
          그러나 실무에서 싱글톤 빈으로 대부분의 문제를 해결할 수 있기 때문에 프로토타입 빈을 직접적으로 사용하는 일은 매우 드물다.

          ObjectProvider는 DL을 위한 편의 기능을 많이 제공해주고 스프링 외에 별도의 의존관계 추가가 없기 때문에 편리하다.
          만약 코드를 스프링이 아닌 다른 컨테이너에서도 사용할 수 있어야 한다면 JSR-330 Provider를 사용해야 한다.

          스프링을 사용하다 보면 이 기능 뿐만 아니라 다른 기능들도 자바 표준과 스프링이 제공하는 기능이 겹칠 떄가 많이 있다.
          대부분 스프링이 더 다양하고 편리한 기능을 제공해주기 때문에,
          특별히 다른 컨테이너를 사용할 일이 없다면, 스프링이 제공하는 기능을 사용하면 된다.

         */


        public int logic() {
            // private ObjectProvider<PrototypeBean> prototypeBeanProvider의 getObject
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();

            // private Provider<PrototypeBean> prototypeBeanProvide의 get
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init / " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
