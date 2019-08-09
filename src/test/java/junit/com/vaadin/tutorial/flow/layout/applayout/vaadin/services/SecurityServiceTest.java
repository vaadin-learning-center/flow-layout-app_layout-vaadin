package junit.com.vaadin.tutorial.flow.layout.applayout.vaadin.services;

import com.vaadin.tutorial.flow.layout.applayout.vaadin.services.SecurityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.frp.model.Result;

public class SecurityServiceTest {

  @Test
  void test001() {
    final SecurityService service = new SecurityService();
    Assertions.assertThrows(NullPointerException.class, () -> {
      final Result<SecurityService.User> result = service.checkLogin(null, null);
    });
  }

  @Test
  void test002() {
    final SecurityService              service = new SecurityService();
    final Result<SecurityService.User> result  = service.checkLogin("", "");
    Assertions.assertTrue(result.isAbsent());
  }

  @Test
  void test003() {
    final SecurityService              service = new SecurityService();
    final Result<SecurityService.User> result  = service.checkLogin("xx", "xx");
    Assertions.assertTrue(result.isAbsent());
  }

  @Test
  void test004() {
    final SecurityService              service = new SecurityService();
    final Result<SecurityService.User> result  = service.checkLogin("admin", "admin");
    Assertions.assertTrue(result.isPresent());
    final SecurityService.User user = result.get();
    Assertions.assertEquals("Jon Doe", user.getT1());
  }
}






