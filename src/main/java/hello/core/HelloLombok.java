package hello.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloLombok {
    /*
    section7. 의존관계 자동 주입 - 롬복과 최신 트랜드

     * 설정
      1. build.gradle에 다음 추가
        //lombok 설정
        configurations {
            compileOnly {
                extendsFrom annotationProcessor
            }
        }

        dependencies {
            implementation 'org.springframework.boot:spring-boot-starter'

            //lombok 라이브러리 추가
            compileOnly 'org.projectlombok:lombok'
            annotationProcessor 'org.projectlombok:lombok'

            testCompileOnly 'org.projectlombok:lombok'
            testAnnotationProcessor 'org.projectlombok:lombok'
            //lombok 라이브러리 추가 끝

            testImplementation 'org.springframework.boot:spring-boot-starter-test'
        }

      2. plugin: lombok 추가
      3. 설정 > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing 체크
     */

    private String name;
    private int age;

    public static void main(String[] args) {
         HelloLombok helloLombok = new HelloLombok();
         helloLombok.setName("hello lombok");

         String name = helloLombok.getName();
         System.out.println("name = " + name);
    }
}
