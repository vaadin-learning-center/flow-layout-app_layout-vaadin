package com.vaadin.tutorial.flow.layout.applayout.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.services.SecurityService;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main.MainView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.DashboardView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.ProfileView;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.TrendsView;

import java.util.HashMap;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

@Theme(Lumo.class)
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
public class MainLayout extends AppLayout {

  private static final String LOGO_PNG = "logo.png";

  public static final String ITM_DASHBOARD = "mainview.menue.item.dashboard";
  public static final String ITM_PROFILE = "mainview.menue.item.profile";
  public static final String ITM_TRENDS = "mainview.menue.item.trends";
  public static final String ITM_LOGOUT = "mainview.menue.item.logout";


  private Map<Tab, Component> tab2Workspace = new HashMap<>();


  public MainLayout() {
    StreamResource res = new StreamResource(LOGO_PNG ,
                                            () -> MainView.class.getResourceAsStream("/" + LOGO_PNG));
    Image img = new Image(res , "Vaadin Logo");
    img.setHeight("44px");
    addToNavbar(new DrawerToggle(), img);

    final Tabs tabs = new Tabs(dashBoard(), user(), trends(), logout());
    tabs.setOrientation(Tabs.Orientation.VERTICAL);
    tabs.addSelectedChangeListener(event -> {
      final Tab selectedTab = event.getSelectedTab();
      final Component component = tab2Workspace.get(selectedTab);
      setContent(component);
    });
    addToDrawer(tabs);

    setContent(new Span("click in the menu ;-) , you will see me never again.."));
  }

  private Tab logout() {
    final Icon icon  = SIGN_OUT.create();
    final Button btn = new Button();
    btn.setText(getTranslation(ITM_LOGOUT));
    btn.setIcon(icon);
    btn.addClickListener(e -> {
      UI ui = UI.getCurrent();
      VaadinSession session = ui.getSession();
      session.setAttribute(SecurityService.User.class , null);
      session.close();
      ui.navigate(MainView.class);
    });
    btn.setSizeFull();
    final Tab    tab   = new Tab(btn);
//    tab2Workspace.put(tab, new TrendsView());
    return tab;
  }

  private Tab trends() {
    final Span label = new Span(getTranslation(ITM_TRENDS));
    final Icon icon  = TRENDING_UP.create();
    final Tab  tab   = new Tab(new HorizontalLayout(icon,label));
    tab2Workspace.put(tab, new TrendsView());
    return tab;
  }

  private Tab user() {
    final Span label = new Span(getTranslation(ITM_PROFILE));
    final Icon icon  = USER.create();
    final Tab  tab   = new Tab(new HorizontalLayout(icon,label));
    tab2Workspace.put(tab, new ProfileView());
    return tab;
  }

  private Tab dashBoard() {
    final Span label = new Span(getTranslation(ITM_DASHBOARD));
    final Icon icon  = DASHBOARD.create();
    final Tab  tab   = new Tab(new HorizontalLayout(icon,label));
    tab2Workspace.put(tab, new DashboardView());
    return tab;
  }

}
