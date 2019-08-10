package junit.com.vaadin.tutorial.flow.layout;

import junit.com.vaadin.tutorial.flow.ServletContainerExtension;
import junit.com.vaadin.tutorial.flow.WebDriverParameterResolver;
import junit.com.vaadin.tutorial.flow.WebdriverExtension;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(ServletContainerExtension.class)
@ExtendWith(WebdriverExtension.class)
@ExtendWith(WebDriverParameterResolver.class)
@EnabledIfEnvironmentVariable(named = "TESTBENCH", matches = "on") //in default annotation
public @interface VaadinTutorial { }
