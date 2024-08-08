package com.simple.basic.config;

import com.simple.basic.command.TestVO;
import com.simple.basic.controller.HomeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //이거 스프링 설정파일이야
//@PropertySource("classpath:/hello.properties") //특정 properties파일을 참조받고 싶다면...
public class WebConfig implements WebMvcConfigurer {

    /*
    @Value("${server.port}") //application.properties파일의 키값을 읽어서 받아옴
    String port;

    @Value("${hello}")
    String hello;

    @Value("${bye}")
    String bye;

    @Autowired
    ApplicationContext applicationContext; //IOC컨테이너
    
    //자바코드로 빈생성
    @Bean
    public TestVO testVO() {
        return new TestVO(); //bean으로 등록
    }

    @Bean //스프링이 이 코드를 실행시켜서, 리턴에 담기는 값을 bean으로 등록
    public void test() {

        System.out.println("스프링 설정파일 실행됨");
        int result = applicationContext.getBeanDefinitionCount();
        System.out.println("context안에 bean의 개수:" + result);
        HomeController home = applicationContext.getBean(HomeController.class);
        System.out.println("context안에 home컨트롤러빈:" + home);

        TestVO vo = applicationContext.getBean(TestVO.class);
        System.out.println("context안에 testVO빈:" + vo);

        System.out.println("properties server.port값:" + port);
        System.out.println("properties hello값:" + hello);
        System.out.println("properties bye값:" + bye);
    }
    */

}
