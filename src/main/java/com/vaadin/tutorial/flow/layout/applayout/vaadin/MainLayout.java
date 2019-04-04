package com.vaadin.tutorial.flow.layout.applayout.vaadin;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;
import static com.vaadin.flow.component.icon.VaadinIcon.SIGN_OUT;
import static com.vaadin.flow.component.icon.VaadinIcon.TRENDING_UP;
import static com.vaadin.flow.component.icon.VaadinIcon.USER;

import com.vaadin.tutorial.flow.layout.applayout.vaadin.services.SecurityService;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main.MainView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.DashboardView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.ProfileView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.TrendsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
public class MainLayout extends AbstractAppRouterLayout {

  private static final String LOGO_PNG = "logo.png";

  public static final String ITM_DASHBOARD = "mainview.menue.item.dashboard";
  public static final String ITM_PROFILE = "mainview.menue.item.profile";
  public static final String ITM_TRENDS = "mainview.menue.item.trends";
  public static final String ITM_LOGOUT = "mainview.menue.item.logout";

  @Override
  protected void configure(AppLayout appLayout ,
                           AppLayoutMenu appLayoutMenu) {

    StreamResource res = new StreamResource(LOGO_PNG ,
                                            () -> MainView.class.getResourceAsStream("/" + LOGO_PNG));
    Image img = new Image(res , "Vaadin Logo");

    img.setHeight("44px");
    appLayout.setBranding(img);

    appLayoutMenu
        .addMenuItems(
            new AppLayoutMenuItem(DASHBOARD.create() ,
                                  appLayout.getTranslation(ITM_DASHBOARD) ,
                                  DashboardView.NAV) ,
            new AppLayoutMenuItem(USER.create() ,
                                  appLayout.getTranslation(ITM_PROFILE) ,
                                  ProfileView.NAV) ,
            new AppLayoutMenuItem(TRENDING_UP.create() ,
                                  appLayout.getTranslation(ITM_TRENDS) ,
                                  TrendsView.NAV) ,
            new AppLayoutMenuItem(SIGN_OUT.create() ,
                                  appLayout.getTranslation(ITM_LOGOUT) ,
                                  e -> {
                                    UI ui = UI.getCurrent();
                                    VaadinSession session = ui.getSession();
                                    session.setAttribute(SecurityService.User.class , null);
                                    session.close();
                                    ui.navigate(MainView.class);
                                  }));
  }

}
