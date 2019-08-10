package junit.com.vaadin.tutorial.flow;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.commands.TestBenchCommandExecutor;
import com.vaadin.testbench.commands.TestBenchCommands;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.rapidpm.frp.Transformations;
import org.rapidpm.frp.functions.CheckedPredicate;
import org.rapidpm.frp.functions.CheckedSupplier;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;
import static org.rapidpm.frp.StringFunctions.notEmpty;
import static org.rapidpm.frp.StringFunctions.notStartsWith;
import static org.rapidpm.frp.Transformations.not;

public class WebDriverParameterResolver
    implements ParameterResolver {

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    final Class<?> type = parameterContext.getParameter()
                                          .getType();
    return GenericPageObject.class.isAssignableFrom(type);
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {

    final BrowserWebDriverContainer container = extensionContext.getStore(GLOBAL)
                                                                .get(BrowserWebDriverContainer.class.getSimpleName(),
                                                                     BrowserWebDriverContainer.class);


    final String  property = System.getProperty("os.name");
    final boolean osx      = property.contains("Mac OS X");

//    return new WebDriverInfo(container.getWebDriver(), (osx)
//                                                       ? "host.docker.internal"
//                                                       : localeIP().get(), container.getVncAddress());


    return new GenericPageObject(container.getWebDriver(), localeIP().get(), container.getVncAddress());
  }

  public static class GenericPageObject {
//    private WebDriver         webdriver;
    private String            hostIpAddress;
    private String            vncAdress;
    final   TestBenchTestCase testCase = new TestBenchTestCase() { };

    public GenericPageObject(WebDriver webdriver, String hostIpAddress, String vncAdress) {
      this.testCase.setDriver(webdriver);
      this.hostIpAddress = hostIpAddress;
      this.vncAdress     = vncAdress;
    }

    public WebDriver getWebdriver() {
      return testCase.getDriver();
    }

    public String getHostIpAddress() {
      return hostIpAddress;
    }

    public String getVncAdress() {
      return vncAdress;
    }

    public TestBenchCommands testBench() {
      return testCase.testBench();
    }

    public SearchContext getContext() {
      return testCase.getContext();
    }

    public TestBenchCommandExecutor getCommandExecutor() {
      return testCase.getCommandExecutor();
    }

    public WebElement findElement(By by) {
      return testCase.findElement(by);
    }

    public List<WebElement> findElements(By by) {
      return testCase.findElements(by);
    }

    public <T extends TestBenchElement> T wrap(Class<T> elementType, WebElement element) {
      return testCase.wrap(elementType, element);
    }

    public <T extends TestBenchElement> ElementQuery<T> $(Class<T> clazz) {
      return testCase.$(clazz);
    }

    public ElementQuery<TestBenchElement> $(String tagName) {
      return testCase.$(tagName);
    }

    public void load(String url) {
      getWebdriver().get(url);
    }

    public String getCurrentUrl() {
      return getWebdriver().getCurrentUrl();
    }

    public String getTitle() {
      return getWebdriver().getTitle();
    }

    public String getPageSource() {
      return getWebdriver().getPageSource();
    }

    public void close() {
      getWebdriver().close();
    }

    public void quit() {
      getWebdriver().quit();
    }

    public Set<String> getWindowHandles() {
      return getWebdriver().getWindowHandles();
    }

    public String getWindowHandle() {
      return getWebdriver().getWindowHandle();
    }

    public WebDriver.TargetLocator switchTo() {
      return getWebdriver().switchTo();
    }

    public WebDriver.Navigation navigate() {
      return getWebdriver().navigate();
    }

    public WebDriver.Options manage() {
      return getWebdriver().manage();
    }
  }

  public static Supplier<String> localeIP() {
    return () -> {
      final CheckedSupplier<Enumeration<NetworkInterface>> checkedSupplier = NetworkInterface::getNetworkInterfaces;

      return Transformations.<NetworkInterface>enumToStream().apply(
          checkedSupplier.getOrElse(Collections::emptyEnumeration))
                                                             .filter(
                                                                 (CheckedPredicate<NetworkInterface>) NetworkInterface::isUp)
                                                             .map(NetworkInterface::getInetAddresses)
                                                             .flatMap(
                                                                 iaEnum -> Transformations.<InetAddress>enumToStream().apply(
                                                                     iaEnum))
                                                             .filter(inetAddress -> inetAddress instanceof Inet4Address)
                                                             .filter(not(InetAddress::isMulticastAddress))
                                                             .filter(not(InetAddress::isLoopbackAddress))
                                                             .map(InetAddress::getHostAddress)
                                                             .filter(notEmpty())
                                                             .filter(adr -> notStartsWith().apply(adr, "127"))
                                                             .filter(adr -> notStartsWith().apply(adr, "169.254"))
                                                             .filter(
                                                                 adr -> notStartsWith().apply(adr, "255.255.255.255"))
                                                             .filter(
                                                                 adr -> notStartsWith().apply(adr, "255.255.255.255"))
                                                             .filter(adr -> notStartsWith().apply(adr, "0.0.0.0"))
                                                             // .filter(adr -> range(224, 240).noneMatch(nr -> adr.startsWith(valueOf(nr))))
                                                             .findFirst()
                                                             .orElse("localhost");
    };
  }
}
