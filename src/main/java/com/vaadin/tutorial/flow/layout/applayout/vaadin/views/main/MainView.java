package com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = MainView.NAV_MAIN_VIEW, layout = MainLayout.class)
public class MainView extends Composite<Div> implements HasLogger {
  public static final String NAV_MAIN_VIEW = "main";

  public MainView() {
    getContent().add(new Span("Page content"));
  }

}
