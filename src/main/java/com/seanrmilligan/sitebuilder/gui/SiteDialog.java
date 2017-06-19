package com.seanrmilligan.sitebuilder.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by sean on 6/5/17.
 */
public class SiteDialog extends Stage {
    Scene scene;
    private GridPane pane;
    private TextField siteName;
    private TextField siteDomain;
    private Button submit;

    public SiteDialog(Stage owner) {
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);

        this.pane = new GridPane();
        this.siteName = new TextField();
        this.siteDomain = new TextField();
        this.submit = new Button();

        this.submit.setOnAction(e -> {
            SiteDialog.this.hide();
        });

        this.pane.add(this.siteName, 0, 0, 3, 1);
        this.pane.add(this.siteDomain, 0, 1, 3, 1);
        this.pane.add(this.submit, 1, 2, 1,1 );
        this.pane.setPadding(new Insets(10));
        this.pane.setHgap(5);
        this.pane.setVgap(5);

        this.scene = new Scene(this.pane);
        this.setScene(this.scene);
    }

    public void init(String title, String submitText) {
        this.setTitle(title);
        this.siteName.setText("");
        this.siteDomain.setText("");
        this.submit.setText(submitText);
    }

    public String getSiteName() {
        return this.siteName.getText();
    }
    public String getSiteDomain() { return this.siteDomain.getText(); }
}
