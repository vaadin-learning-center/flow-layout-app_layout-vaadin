package com.vaadin.tutorial.flow.layout.applayout.vaadin.services.flow;

import static com.vaadin.tutorial.flow.layout.applayout.vaadin.views.login.LoginViewOO.NAV_LOGIN_VIEW;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.services.SecurityService;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.login.LoginViewOO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.UIInitListener;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

public class ApplicationServiceInitListener implements VaadinServiceInitListener, HasLogger {

  @Override
  public void serviceInit(ServiceInitEvent e) {

    e.getSource()
     .addUIInitListener((UIInitListener) uiInitEvent -> {
       logger().info("init SecurityListener for .. " + uiInitEvent.getUI());
       uiInitEvent.getUI().addBeforeEnterListener(new SecurityListener());
     });
  }

  private static class SecurityListener implements BeforeEnterListener, HasLogger {
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
      final UI ui = UI.getCurrent();
      final VaadinSession vaadinSession = ui.getSession();

      Result.ofNullable(vaadinSession
                            .getAttribute(SecurityService.User.class))
            .ifPresentOrElse(u -> {
                               logger().info("User is logged in : " + u);
                             } ,
                             failed -> {
                               logger().info("Anonymous User: redirecting to Login View");
                               if (! beforeEnterEvent.getNavigationTarget().equals(LoginViewOO.class))
                                 beforeEnterEvent.rerouteTo(NAV_LOGIN_VIEW);
                             });

    }
  }
}
