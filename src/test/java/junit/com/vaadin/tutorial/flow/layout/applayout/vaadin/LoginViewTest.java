package junit.com.vaadin.tutorial.flow.layout.applayout.vaadin;


import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.login.LoginViewOO;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main.MainView;
import junit.com.vaadin.tutorial.flow.WebDriverParameterResolver;
import junit.com.vaadin.tutorial.flow.WebDriverParameterResolver.GenericPageObject;
import junit.com.vaadin.tutorial.flow.layout.VaadinTutorial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.dependencies.core.logger.HasLogger;

@VaadinTutorial
public class LoginViewTest
    implements HasLogger {

  @Test
  void test001(GenericPageObject pageObject) {
    final String url = "http://" + pageObject.getHostIpAddress() + ":8899/";
    logger().info("URL : " + url);

    pageObject.load(url);

    pageObject.$(TextFieldElement.class)
            .id(LoginViewOO.USERNAME)
            .setValue("admin");
    pageObject.$(PasswordFieldElement.class)
            .id(LoginViewOO.PASSWORD)
            .setValue("admin");
    pageObject.$(ButtonElement.class)
            .id(LoginViewOO.BTN_LOGIN)
            .click();

    final String text = pageObject.$(SpanElement.class)
                                .id(MainView.PAGE_CONTENT_ID)
                                .getText();
    Assertions.assertEquals(MainView.PAGE_CONTENT, text);
  }

  private void login() {

  }
}
