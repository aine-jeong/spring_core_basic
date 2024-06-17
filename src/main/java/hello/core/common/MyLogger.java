package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope(value = "request")
public class MyLogger {

    /*
    proxyMode = ScopedProxyMode.TARGET_CLASS
    - 적용 대상이 인터페이스가 아닌 클래스면 TARGET_CLASS, 인터페이스면 "INTERFACES"
    - MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해둘 수 있다.

    - 내부에 실제 MyLogger의 참조를 가지고있다가 요청받았을 때 request스코프의 진짜 'myLogger.logic()'을 호출한다.
    - 가짜 프록시 객체는 요청이 오면 그떄 내부에서 진짜 빈을 요청하는 위임 로직이 들어있다.
    - 가짜 프록시 객체는 실제 request scope와는 관계가 없다. 그냥 가짜이며 내부에 단순한 위임 로직이 있고, 싱글톤 처럼 동작한다.
         -> 다형성의 힘!

    - 프록시 객체 덕분에 클라이언트는 마치 싱글톤 빈을 사용하듯 편리하게 request scopefmf tkdydgkf tn dlTek.
    - 사실 Provider를 사용하든, 프록시를 사용하든 핵심 아이디어는 진짜 객체 조회를 "꼭 필요한 시점까지 지연처리"한다는 점이다.
    - 단지 애노테이션 설정 변경만으로 원본 객체를 프록시 객체로 대체할 수 있다. 이것이 바로 다형성과 DI 컨테이너가 가진 큰 강점이다.

    * 주의점
        - 마치 싱글톤을 사용하는 것 같지만, 다르게 동작하기 때문에 주의해서 사용해야 한다.
        - 이런 특별한 scope는 꼭 필요한 곳에서만 사용해야 한다. 무분별하게 사용시 유지보수와 테스트가 어려워진다.

     */

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        // requestURL은 이 빈이 생성되는 시점에는 알 수 없으므로 외부에서 setter를 이용해 주입받는다.
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "][" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}
