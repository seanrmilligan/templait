package com.seanrmilligan.sitebuilder.view.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by sean on 6/5/17.
 */
public class TextInputDialog extends Stage {
    Scene scene;
    private GridPane pane;
    private TextField field;
    private Button submit;

    public TextInputDialog(Stage owner) {
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);


        this.pane = new GridPane();
        this.field = new TextField();
        this.submit = new Button();

        this.submit.setOnAction(e -> {
            TextInputDialog.this.close();
        });

        this.pane.add(this.field, 0, 0);
        this.pane.add(this.submit, 1, 0);

        this.scene = new Scene(this.pane);
        this.setScene(this.scene);
    }

    public void show(String title, String submitText) {
        this.setTitle(title);
        this.submit.setText(submitText);
        this.show();
    }

    public String getText() {
        return this.field.getText();
    }
}
