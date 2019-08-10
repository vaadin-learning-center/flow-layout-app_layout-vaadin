package junit.com.vaadin.tutorial.flow.layout.applayout.vaadin;


import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.login.LoginViewOO;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main.MainView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.vaadin.tutorial.nano.jetty.junit5.VaadinTutorial;
import org.vaadin.tutorial.nano.jetty.junit5.WebDriverParameterResolver.GenericPageObject;

@VaadinTutorial
public class LoginViewTest
    implements HasLogger {


  public static class LoginViewPageObject
      extends GenericPageObject {
    private final TestBenchTestCase testCase = new TestBenchTestCase() { };

    public LoginViewPageObject(WebDriver webdriver, String hostIpAddress, String vncAdress) {
      super(webdriver, hostIpAddress, vncAdress);
      this.testCase.setDriver(webdriver);
    }

    public void load(String url) {
      testCase.getDriver()
              .get(url);
    }

    public WebDriver getDriver() {
      return testCase.getDriver();
    }

    public void setDriver(WebDriver driver) {
      testCase.setDriver(driver);
    }

    public <T extends TestBenchElement> ElementQuery<T> $(Class<T> clazz) {
      return testCase.$(clazz);
    }

    public ElementQuery<TestBenchElement> $(String tagName) {
      return testCase.$(tagName);
    }

    public TextFieldElement username() {
      return $(TextFieldElement.class).id(LoginViewOO.USERNAME);
    }

    public PasswordFieldElement password() {
      return $(PasswordFieldElement.class).id(LoginViewOO.PASSWORD);
    }

    public ButtonElement btn() {
      return $(ButtonElement.class).id(LoginViewOO.BTN_LOGIN);
    }

    public SpanElement counter(){
      return $(SpanElement.class)
          .id(MainView.PAGE_CONTENT_ID);
    }
  }


  @Test
  void test001(LoginViewPageObject pageObject) {
    final String url = "http://" + pageObject.getHostIpAddress() + ":8899/";
    logger().info("URL : " + url);

    pageObject.load(url);

    pageObject.username().setValue("admin");
    pageObject.password().setValue("admin");
    pageObject.btn().click();

    final String text = pageObject.counter().getText();
    Assertions.assertEquals(MainView.PAGE_CONTENT, text);
  }

}
