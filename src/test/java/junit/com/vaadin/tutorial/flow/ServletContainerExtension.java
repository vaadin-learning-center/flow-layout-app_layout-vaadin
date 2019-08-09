package junit.com.vaadin.tutorial.flow;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.vaadin.tutorial.nano.jetty.CoreUIService;

public class ServletContainerExtension
    implements BeforeEachCallback, AfterEachCallback {

  @Override
  public void beforeEach(ExtensionContext ctx) throws Exception {
//    Stagemonitor.init();
    final CoreUIService uiService = new CoreUIService();
    uiService.startup();
    ctx.getStore(ExtensionContext.Namespace.GLOBAL)
       .put(CoreUIService.class.getSimpleName(), uiService);
  }


  @Override
  public void afterEach(ExtensionContext ctx) throws Exception {
    ctx.getStore(ExtensionContext.Namespace.GLOBAL)
       .get(CoreUIService.class.getSimpleName(), CoreUIService.class).jetty.ifPresent(server -> {
      try {
        server.stop();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
