package com.vaadin.tutorial.flow.layout.applayout.vaadin.services;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.Locale.ROOT;
import static java.util.ResourceBundle.getBundle;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.Transformations;

public class ResourceBundleService implements HasLogger {

  public static final String RESOURCE_BUNDLE_NAME = "labels";

  private static final ResourceBundle RESOURCE_BUNDLE_ROOT = getBundle(RESOURCE_BUNDLE_NAME , ROOT);
  private static final ResourceBundle RESOURCE_BUNDLE_EN = getBundle(RESOURCE_BUNDLE_NAME , ENGLISH);
  private static final ResourceBundle RESOURCE_BUNDLE_DE = getBundle(RESOURCE_BUNDLE_NAME , GERMAN);

  private static Map<Locale, ResourceBundle> persistenceStorage = new ConcurrentHashMap<>();

  public ResourceBundleService() {
    postProcess();
  }

  //don´t remove from the Set itself -> will be reflected to the map
  public Supplier<Stream<Locale>> providedLocalesAsSupplier() {
    return this::providedLocalesAsStream;
  }

  public Stream<Locale> providedLocalesAsStream() {
    return persistenceStorage
        .keySet()
        .stream()
        .filter((l) -> ! l.equals(ROOT));
  }

  public List<Locale> providedLocalesAsList() {
    return providedLocalesAsStream()
        .collect(toList());
  }


  public Function<Locale, ResourceBundle> resourceBundleToUse() {
    return (locale) -> convertLocale()
        .andThen(loadResourceBundle())
        .apply(locale);
  }


  private Function<Locale, Locale> convertLocale() {
    return Transformations
        .<Stream<Locale>, Locale, Locale>curryBiFunction()
        .apply(selectLocaleToUse())
        .apply(providedLocalesAsStream());
  }

  private BiFunction<Stream<Locale>, Locale, Locale> selectLocaleToUse() {
    return (availableLocales , requestedLocale) -> availableLocales
        .filter((l) -> l.equals(requestedLocale))
        .findFirst()
        .orElse(ROOT);
  }

  private Function<Locale, ResourceBundle> loadResourceBundle() {
    return (locale) -> persistenceStorage.get(locale);
  }

  //LifeCycle dependent
  private void postProcess() {
    persistenceStorage.put(ROOT , RESOURCE_BUNDLE_ROOT);
    persistenceStorage.put(ENGLISH , RESOURCE_BUNDLE_EN);
    persistenceStorage.put(GERMAN , RESOURCE_BUNDLE_DE);
  }


}
