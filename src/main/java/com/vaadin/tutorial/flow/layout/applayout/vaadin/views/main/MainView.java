package com.vaadin.tutorial.flow.layout.applayout.vaadin.views.main;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.flow.layout.applayout.vaadin.MainLayout;
import org.rapidpm.dependencies.core.logger.HasLogger;

@Route(value = MainView.NAV_MAIN_VIEW, layout = MainLayout.class)
public class MainView extends Composite<Div> implements HasLogger {
  public static final String NAV_MAIN_VIEW = "main";
  public static final String PAGE_CONTENT_ID = "page-content";
  public static final String PAGE_CONTENT = "Page content";

  public MainView() {
    final Span pageContent = new Span(PAGE_CONTENT);
    pageContent.setId(PAGE_CONTENT_ID);
    getContent().add(pageContent);
  }

}
