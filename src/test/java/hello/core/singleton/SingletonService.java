package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    // 자기 자신을 내부에 "static"으로 가지고 있다 => 클래스 레벨에 올라가기 때문에 딱 하나만 존재하게 된다.

    public static SingletonService getInstance() {
        return instance;
    }

    //private로 생성자를 만들어서 외부에서 new로 생성하는 것을 막아버림
    private SingletonService() {

    }

    /*
    1. static 영역에 객체 Instance를 미리 하나 생성해서 올려둔다.
    2. 이 객체 인스턴스가 필요하면 오직 'getInstance()' 메서드를 통해서만 조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
    3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 ㅔrivate로 막아서 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.

    싱글톤 패턴을 구현하는 방법은 여러가지가 있다. 여기서는 객체를 미리 생성해두는 가장 단순하고 안전한 방법을 선택한 것.

    - 싱글톤 패턴의 문제점
    1. 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
    2. 의존 관계상 클라이언트가 구체 클래스에 의존한다. -> DIP 위반
    3. 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
    4. 테스트하기 어렵다.
    5. 내부 속성을 변경하거나 초기화 하기 어렵다.
    6. private 생성자로 자식 클래스를 만들기 어렵다.
    7. 결론적으로 유연성이 떨어진다.
    8. 안티패턴으로 불리기도 한다.
    But, 스프링 컨테이너가 해결해준당~

    "싱글톤 컨테이너"
    - 스프링 컨테이너는 싱글톤 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다.
    - 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 한다.

     */

    public static void main(String[] args) {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
