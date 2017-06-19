package com.seanrmilligan.sitebuilder.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.seanrmilligan.sitebuilder.view.Constants.MIN_HEIGHT;
import static com.seanrmilligan.sitebuilder.view.Constants.MIN_WIDTH;

/**
 * Created by sean on 6/5/17.
 */
public class SiteDataDialog extends Stage {
    Scene scene;
    private GridPane pane;
    private TextField siteName;
    private TextField siteDomain;
    private Button submit;

    public SiteDataDialog(Stage owner) {
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);

        this.pane = new GridPane();
        this.siteName = new TextField();
        this.siteDomain = new TextField();
        this.submit = new Button();

        this.submit.setOnAction(e -> {
            SiteDataDialog.this.hide();
        });

        this.pane.add(this.siteName, 0, 0, 3, 1);
        this.pane.add(this.siteDomain, 0, 1, 3, 1);
        this.pane.add(this.submit, 1, 2, 1,1 );
        this.pane.setPadding(new Insets(10));
        this.pane.setHgap(5);
        this.pane.setVgap(5);

        this.pane.setMinWidth(MIN_WIDTH);
        this.pane.setMinHeight(MIN_HEIGHT);
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
    public void setSiteName(String name) { this.siteName.setText(name); }
    public String getSiteDomain() { return this.siteDomain.getText(); }
    public void setSiteDomain(String domain) { this.siteDomain.setText(domain); }
}
