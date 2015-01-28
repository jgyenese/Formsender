package org.vaadin.risto.formsender.widgetset.client.shared;

import java.util.Map;

import org.vaadin.risto.formsender.FormSender;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.Element;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;

@Connect(FormSender.class)
public class FormSenderConnector extends AbstractExtensionConnector {

  private static final long serialVersionUID = 5721950329134512038L;
  private Element targetElement;

  private final FormControl formControlRpc = new FormControl() {
    private static final long serialVersionUID = -6376909637382144413L;

    @Override
    public void send(Method method, Map<String, String[]> values,
                     String target, String action, String formAcceptCharSet) {
      createAndSendForm(method.toString(), values, target, action, formAcceptCharSet);
    }
  };

  @Override
  protected void init() {
    super.init();
    registerRpc(FormControl.class, formControlRpc);
  }

  @Override
  public void onUnregister() {
    unregisterRpc(FormControl.class, formControlRpc);
    super.onUnregister();
  }

  @Override
  protected void extend(ServerConnector target) {
    targetElement = (( ComponentConnector ) target).getWidget().getElement();
  }

  protected void createAndSendForm(String method, Map<String, String[]> values, String target, String action, String acceptCharSet) {
    FormElement formElement = createFormElement(method, values, target, action, acceptCharSet);
    formElement.setClassName("formsender");
    targetElement.appendChild(formElement);
    formElement.submit();
  }

  // create the form element to be submitted
  protected FormElement createFormElement(String method, Map<String, String[]> values, String target, String action, String acceptCharSet) {
    FormElement fe = Document.get().createFormElement();
    fe.setMethod(method);
    fe.setTarget(target);
    fe.setAction(action);
    if( acceptCharSet != null ) {
      fe.setAcceptCharset(acceptCharSet);
    }
    for( Map.Entry<String, String[]> nameValue : values.entrySet() ) {
      for( String value : nameValue.getValue() ) {
        InputElement inputElement = Document.get().createHiddenInputElement();
        inputElement.setName(nameValue.getKey());
        inputElement.setValue(value);
        fe.appendChild(inputElement);
      }
    }
    return fe;
  }
}