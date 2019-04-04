package com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages;

import static com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.DashboardView.NAV;

import com.vaadin.tutorial.flow.layout.applayout.vaadin.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value= NAV ,layout = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public static final String NAV = "dashbord";

  public DashboardView() {
    getContent().add(new Span("Dashboard"));
  }
}
