package ru.innopolis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.ipc.netty.NettyContext;
import ru.innopolis.server.Server;
import ru.innopolis.service.sec.SecurityConfig;

@Configuration
@EnableWebFlux
@ComponentScan("ru.innopolis")
//@Import({SecurityConfig.class})
@PropertySource(value={"classpath:application.properties"})
public class WebConfig extends WebFluxConfigurationSupport {

    private static final Logger logger = LogManager.getLogger(WebConfig.class.getName());

    @Value("classpath:/templates/index.html")
    private Resource indexHtml;

    @Value("${server.port}")
    private Integer serverPort;

    @Value("${server.host}")
    private String serverHost;

    private Server server;

//    @Bean
//    RouterFunction<?> routerFunction() {
//        RouterFunction router = RouterFunctions.
//                route(GET("/*"), request -> {
//                    return ServerResponse.ok().contentType(TEXT_HTML).syncBody(indexHtml);
//                });
//        return router;
//    }

    @Bean
    public NettyContext nettyContext(ApplicationContext context) {
        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context)
                .build();
        return server.startReactorServer(serverHost, serverPort, handler);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Autowired
    public void setServer(Server server) {
        this.server = server;
    }

}
