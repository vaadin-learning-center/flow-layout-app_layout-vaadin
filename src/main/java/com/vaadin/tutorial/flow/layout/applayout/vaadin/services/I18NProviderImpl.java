package com.vaadin.tutorial.flow.layout.applayout.vaadin.services;

import static java.util.Locale.ROOT;
import static java.util.Objects.isNull;
import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.flow.i18n.I18NProvider;

public class I18NProviderImpl implements I18NProvider, HasLogger {

  public static final String NULL_KEY = "###-NULL-KEY-###";
  public static final String EMPTY_KEY = "###-EMPTY-KEY-###";

  public I18NProviderImpl() {
    logger().info("I18NProviderImpl was found..");
  }


  private final ResourceBundleService resourceBundleService = new ResourceBundleService();

  @Override
  public List<Locale> getProvidedLocales() {
    logger().info("getProvidedLocales.. ");
    return resourceBundleService
        .providedLocalesAsList();
  }

  @Override
  public String getTranslation(String key , Locale locale , Object... params) {
    logger().info("VaadinI18NProvider getTranslation.. key : " + key + " - " + locale);


    final ResourceBundle resourceBundle = resourceBundleService
        .resourceBundleToUse()
        .apply(locale != null ? locale : ROOT);

    return match(
        matchCase(() -> failure("###-" + key + "-###-" + locale)) ,
        matchCase(() -> isNull(key) , () -> failure(NULL_KEY)) ,
        matchCase(key::isEmpty , () -> failure(EMPTY_KEY)) ,
        matchCase(() -> resourceBundle.containsKey(key) , () -> success(resourceBundle.getString(key)))
    )
        .ifFailed(msg -> logger().warning(msg))
        .getOrElse(() -> "###-KEY_NOT_FOUND-" + key + " - " + locale + "-###");
  }
}