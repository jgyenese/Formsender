package org.vaadin.risto.formsender;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.risto.formsender.widgetset.client.shared.FormControl;
import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.UI;

/**
 * Component that allows submitting post or get requests from a Vaadin
 * application. Submitting a form with this component will redirect the user
 * away from the current application.
 *
 * @author Risto Yrjänä / Vaadin }>
 */
public class FormSender extends AbstractExtension {

  private static final long serialVersionUID = -2072702453956623077L;

  private Method formMethod;

  private Map<String, String[]> values;

  private String formTarget;

  private String formAction;

  private String formAcceptCharSet;

  public FormSender() {
    this(Method.POST);
  }

  public FormSender(Method formMethod) {
    this.formMethod = formMethod;
    this.values = new HashMap<String, String[]>();
  }

  public void extend(UI target) {
    super.extend(target);
  }

  /**
   * Submit the form with the current values, target, action and method.
   */
  public void submit() {
    getRpcProxy(FormControl.class).send(formMethod, values, formTarget, formAction, formAcceptCharSet);
  }

  /**
   * Set the method to be used when submitting the form.
   *
   * @param formMethod
   */
  public void setFormMethod(Method formMethod) {
    this.formMethod = formMethod;
  }

  public Method getFormMethod() {
    return formMethod;
  }

  /**
   * Set the name / value array pairs to use when submitting the form.
   *
   * @param values the values to set
   */
  public void setValues(Map<String, String[]> values) {
    this.values = values;
  }

  public Map<String, String[]> getValues() {
    return values;
  }

  /**
   * Add a name / value pair.
   *
   * @param name
   * @param value
   */
  public void addValue(String name, String value) {
    values.put(name, new String[]{ value });
  }

  public void addValue(String name, String... values) {
    this.values.put(name, values);
  }

  public void addValues(String name, String[] values) {
    this.values.put(name, values);
  }

  public void addValues(Map<String,String> values) {
    for( Map.Entry<String, String> nameValue : values.entrySet() ) {
      this.values.put(nameValue.getKey(), new String[]{ nameValue.getValue() });
    }
  }

  public void addValuesA(Map<String,String[]> values) {
    this.values.putAll(values);
  }

  /**
   * Set the submit target. E.g. "_blank".
   *
   * @param formTarget
   */
  public void setFormTarget(String formTarget) {
    this.formTarget = formTarget;
  }

  public String getFormTarget() {
    return formTarget;
  }

  public String getFormAction() {
    return formAction;
  }

  /**
   * Set the action of the form. This can be a full valid URL or a part of it
   * relative to the current application.
   *
   * @param formAction
   */
  public void setFormAction(String formAction) {
    this.formAction = formAction;
  }

  /**
   * Specifies the charset used in the submitted form.
   * relative to the current application.
   *
   * @param formAcceptCharSet
   */
  public void setFormAcceptCharSet(String formAcceptCharSet) {
    this.formAcceptCharSet = formAcceptCharSet;
  }
}
