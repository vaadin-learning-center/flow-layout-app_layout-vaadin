package junit.com.vaadin.tutorial.flow.layout.applayout.vaadin;


import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.login.LoginViewOO;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main.MainView;
import junit.com.vaadin.tutorial.flow.ServletContainerExtension;
import junit.com.vaadin.tutorial.flow.WebDriverParameterResolver;
import junit.com.vaadin.tutorial.flow.WebdriverExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rapidpm.dependencies.core.logger.HasLogger;

@ExtendWith(ServletContainerExtension.class)
@ExtendWith(WebdriverExtension.class)
@ExtendWith(WebDriverParameterResolver.class)
public class LoginViewTest
    implements HasLogger {

  private final TestBenchTestCase testCase = new TestBenchTestCase() { };

  @Test
//  @Disabled
  void test001(WebDriverParameterResolver.WebDriverInfo webDriverInfo) {
    logger().info("webDriverInfo = " + webDriverInfo.getVncAdress());

    testCase.setDriver(webDriverInfo.getWebdriver());

    final String url = "http://" + webDriverInfo.getHostIpAddress() + ":8899/";
    logger().info("URL : " + url);

    testCase.getDriver()
            .get(url);

    testCase.$(TextFieldElement.class)
            .id(LoginViewOO.USERNAME)
            .setValue("admin");
    testCase.$(PasswordFieldElement.class)
            .id(LoginViewOO.PASSWORD)
            .setValue("admin");
    testCase.$(ButtonElement.class)
            .id(LoginViewOO.BTN_LOGIN)
            .click();

    final String text = testCase.$(SpanElement.class)
                                .id(MainView.PAGE_CONTENT_ID)
                                .getText();
    Assertions.assertEquals(MainView.PAGE_CONTENT, text);

  }

  private void login() {

  }
}
