package org.vaadin.risto.formsender;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.vaadin.ui.UI;

public class FormSenderBuilder {

  public static FormSenderBuilder create() {
    return new FormSenderBuilder();
  }

  protected FormSenderBuilder() {
    // use FormSenderBuilder.create()
  }

  private final Map<String, String[]> values = new HashMap<String, String[]>();
  private Method method;
  private UI     ui;
  private String action;
  private String target;
  private String acceptCharSet;

  public FormSenderBuilder withMethod(Method method) {
    this.method = method;
    return this;
  }

  public FormSenderBuilder withAction(String action) {
    this.action = action;
    return this;
  }

  public FormSenderBuilder withTarget(String target) {
    this.target = target;
    return this;
  }

  public FormSenderBuilder withAcceptCharSet(String acceptCharSet) {
    this.acceptCharSet = acceptCharSet;
    return this;
  }


  public FormSenderBuilder withUI(UI ui) {
    this.ui = ui;
    return this;
  }

  public FormSenderBuilder withValue(String name, String value) {
    values.put(name, new String[]{ value });
    return this;
  }

  public FormSenderBuilder withValue(String name, String... values) {
    this.values.put(name, values );
    return this;
  }

  public FormSenderBuilder withValues(String name, String[] values) {
    this.values.put(name, values );
    return this;
  }

  public FormSenderBuilder withValues(Map<String, String> values) {
    for( Entry<String, String> nameValue : values.entrySet() ) {
      this.values.put(nameValue.getKey(), new String[]{ nameValue.getValue() });
    }
    return this;
  }

  public FormSenderBuilder addValuesA(Map<String,String[]> values) {
    this.values.putAll(values);
    return this;
  }

  public void submit() {
    FormSender fs = new FormSender(method);
    fs.setValues(values);
    fs.setFormAction(action);
    fs.setFormTarget(target);
    fs.setFormAcceptCharSet(acceptCharSet);
    fs.extend(ui);
    fs.submit();
  }
}