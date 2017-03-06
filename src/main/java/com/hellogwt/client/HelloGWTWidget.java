package com.hellogwt.client;

/**
 * Created by Lizzi on 26.02.2017.
 */

        import com.google.gwt.core.client.GWT;
        import com.google.gwt.event.dom.client.ClickEvent;
        import com.google.gwt.uibinder.client.UiBinder;
        import com.google.gwt.uibinder.client.UiField;
        import com.google.gwt.uibinder.client.UiHandler;
        import com.google.gwt.user.client.Window;
        import com.google.gwt.user.client.rpc.AsyncCallback;
        import com.google.gwt.user.client.ui.*;
        import com.hellogwt.client.service.GreetingService;
        import com.hellogwt.client.service.GreetingServiceAsync;
        import com.hellogwt.shared.domain.Greeting;

        import java.util.List;

public class HelloGWTWidget extends Composite {

    interface HelloGWTWidgetUiBinder extends UiBinder<Widget, HelloGWTWidget> {
    }

    private static HelloGWTWidgetUiBinder uiBinder = GWT.create(HelloGWTWidgetUiBinder.class);

    private GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    private AsyncCallback<Void> callback = new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("ERROR: Cannot edit greetings!");
        }

        @Override
        public void onSuccess(Void result) {
            refreshGreetingsTable();
        }
    };

    @UiField
    TextBox NameTextBox;
    @UiField
    TextBox AddressTextBox;
    @UiField
    Button addButton;
   /* @UiField
    Button updateButton;
    @UiField
    Button deleteButton;*/
    @UiField
    FlexTable greetingsFlexTable;

    public HelloGWTWidget() {
        initWidget(uiBinder.createAndBindUi(this));

        refreshGreetingsTable();
    }

    @UiHandler("addButton")
    void handleAddButtonClick(ClickEvent clickEvent) {
        if (!NameTextBox.getText().isEmpty() && !AddressTextBox.getText().isEmpty()) {
            greetingService.getGreeting(AddressTextBox.getText(), new AsyncCallback<Greeting>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("ERROR: Cannot find greeting!");
                }

                @Override
                public void onSuccess(Greeting result) {
                    if (result == null) {
                        greetingService.addGreeting(NameTextBox.getText(), AddressTextBox.getText(),
                                callback);
                    } else {
                        Window.alert("Greeting already exists!");
                    }
                }
            });
        } else {
            Window.alert("\"NAme\" and \"Address\" fields cannot be empty!");
        }
    }


    private void refreshGreetingsTable() {
        greetingService.getGreetings(new AsyncCallback<List<Greeting>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("ERROR: Cannot load greetings!");
            }

            @Override
            public void onSuccess(List<Greeting> greetings) {
                fillGreetingsTable(greetings);
            }
        });
    }

    private void fillGreetingsTable(List<Greeting> greetings) {
        greetingsFlexTable.removeAllRows();

        greetingsFlexTable.setText(0, 0, "Name");
        greetingsFlexTable.setText(0, 1, "Address");

        for (Greeting greeting : greetings) {
            int row = greetingsFlexTable.getRowCount();

            greetingsFlexTable.setText(row, 0, greeting.getName());
            greetingsFlexTable.setText(row, 1, greeting.getAddress());
        }
    }
}