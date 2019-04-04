package com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages;

import static com.vaadin.tutorial.flow.layout.applayout.vaadin.views.pages.ProfileView.*;

import com.vaadin.tutorial.flow.layout.applayout.vaadin.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;


@Route(value=NAV, layout = MainLayout.class)
public class ProfileView extends Composite<Div> {
  public static final String NAV = "profile";

  public ProfileView() {
    getContent().add(new Span("Profile"));
  }
}
