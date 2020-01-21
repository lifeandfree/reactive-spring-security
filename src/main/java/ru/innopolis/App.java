package ru.innopolis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.ipc.netty.NettyContext;

public class App {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(WebConfig.class)) {
            context.getBean(NettyContext.class).onClose().block();
        }
    }
}

