package com.kies.sgsc;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class App {
    
	@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
	
	@Bean
	public TomcatServletWebServerFactory tomcatFactory() {
	   return new TomcatServletWebServerFactory() {
	      @Override
	      protected void postProcessContext(Context context) {
	         ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
	   }};
	}
	
	public static void main(String[] args) {
		//System.setProperty("debug","false");  //default false 
		//System.setProperty("logging.config","/log4j.xml");  
		//9. Web properties
		//System.setProperty("spring.hateoas.use-hal-as-default-json-media-type","true");
		//System.setProperty("spring.http.converters.preferred-json-mapper",""); //HTTP 메시지 변환에 사용할 기본 JSON 매퍼
//		System.setProperty("spring.http.encoding.charset","UTF-8"); //http 인코딩
//		System.setProperty("spring.http.encoding.enabled","true");  //http 인코딩 지원 사용 여부
//		System.setProperty("spring.http.encoding.force","true"); // HTTP 요청 및 응답에서 인코딩을 구성된 문자 세트로 강제 적용할지 여부
//		System.setProperty("spring.http.encoding.force-request","UTF-8");  //HTTP 요청에서 인코딩을 구성된 문자 세트로 강제 적용할지 여부입니다."force"가 지정되지 않은 경우 기본값은 true
		//System.setProperty("spring.http.encoding.force-response","true"); //HTTP 응답에서 구성된 문자 세트로 인코딩을 강제 적용할지 여부
		
		//11. Server properties
//		System.setProperty("server.address",""); //서버가 바인드해야하는 네트워크 주소입니다.
//		System.setProperty("server.compression.enabled","false");//응답 압축이 사용 가능한지 여부
//		System.setProperty("server.compression.excluded-user-agents","");//응답을 압축해서는 안되는 쉼표로 구분 된 사용자 에이전트 목록입니다.
//		System.setProperty("server.compression.mime-types","text/html,.....");// 압축해야하는 쉼표로 구분 된 MIME 유형 목록.
//		System.setProperty("server.compression.min-response-size","2KB"); //압축을 수행하는 데 필요한 최소 "Content-Length"값입니다.
//		System.setProperty("server.error.include-exception","false"); // "예외"속성을 포함하십시오.
		
		//--------------- 에러페이지 세팅--------------------
//		System.setProperty("server.error.include-stacktrace","always"); //스택추적레벨 never 은 트레이스 출력안됨
//		System.setProperty("server.error.path","/error");
//		System.setProperty("server.error.whitelabel.enabled","true");// 서버 오류가 발생하면 브라우저에 표시되는 기본 오류 페이지를 활성화하십시오.
		//--------------- 에러페이지 세팅--------------------	  
		
//		System.setProperty("server.jetty.max-threads","100");
//		System.setProperty("server.jetty.min-threads","10");
		//System.setProperty("server.port","80");
		//System.setProperty("server.servlet.context-path","/str");
		
		
		/*SpringApplication application = new SpringApplication(App .class);
		application.addListeners(new AppStartedSampleListener ());*/
		//application.setWebApplicationType(WebApplicationType.SERVLET); // 웹 어플리케이션 타입 지정

		SpringApplication app = new SpringApplication(App.class);
	    app.addListeners(new ApplicationPidFileWriter()); // ApplicationPidFileWriter 설정
	    app.run(args);
	    
		//SpringApplication.run(App.class, args);
  }
}