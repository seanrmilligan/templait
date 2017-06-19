package com.seanrmilligan.sitebuilder.gui;

import com.seanrmilligan.sitebuilder.model.SiteBuilderView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SiteBuilderWindow implements SiteBuilderView {
    Stage primaryStage;
    Scene scene;
    GridPane pane;
    Label siteNameLabel;
    Label siteName;

    public SiteBuilderWindow (Stage primaryStage){
        this.primaryStage = primaryStage;
        this.pane = new GridPane();
        this.siteNameLabel = new Label("Site Name: ");
        this.siteName = new Label("");
        this.pane.add(this.siteNameLabel, 0, 0);
        this.pane.add(this.siteName, 1, 0);
        this.scene = new Scene(this.pane);
        this.primaryStage.setScene(this.scene);
    }

    public void init(String windowTitle) {
        this.primaryStage.setTitle(windowTitle);
        this.primaryStage.show();
    }

    public void setSiteName(String name) {

    }

    public void setSiteDomain(String domain) {

    }

    public String getText() {
        return null;
    }
}
