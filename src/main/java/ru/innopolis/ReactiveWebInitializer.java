package ru.innopolis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

public class ReactiveWebInitializer extends AbstractReactiveWebInitializer {

    @Override
    protected ApplicationContext createApplicationContext() {
        Class<?>[] configSpringClasses = getConfigClasses();
        Assert.notEmpty(configSpringClasses, "No Spring configuration provided.");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(configSpringClasses);
        return context;
    }

    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class[]{
                WebConfig.class
        };
    }
}
