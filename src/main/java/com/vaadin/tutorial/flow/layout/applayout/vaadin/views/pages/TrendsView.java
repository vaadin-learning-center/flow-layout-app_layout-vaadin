package com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages;


import static com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.TrendsView.*;

import com.vaadin.tutorial.flow.layout.applayout.vaadin.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;


@Route(value=NAV, layout = MainLayout.class)
public class TrendsView extends Composite<Div> {
  public static final String NAV = "trends";


  public TrendsView() {
    getContent().add(new Span("Trends"));
  }
}
