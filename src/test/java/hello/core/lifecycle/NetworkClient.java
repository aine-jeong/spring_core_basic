package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    /*
    데이터베이스 커넥션 풀이나, 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고,
    애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면 객체의 초기화와 종료 작업이 필요하다.
     */

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " / message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }


    /*
    //implements InitializingBean, DisposableBean
    @Override
    public void afterPropertiesSet() throws Exception {
        //빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
        System.out.println("NetworkClient.afterPropertiesSet");;
        connect();
        call("초기화 연결 메시지");
    }

    @Override
    public void destroy() throws Exception {
        //빈이 소멸되기 적전에 호출
        System.out.println("NetworkClient.destroy");
        disconnect();
    }

     => 인터페이스를 이용하는 방식은 현재 잘 사용되지 않음!
     */

    @PostConstruct
    public void init() {
        //빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
        System.out.println("NetworkClient.init");;
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        //빈이 소멸되기 적전에 호출
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
