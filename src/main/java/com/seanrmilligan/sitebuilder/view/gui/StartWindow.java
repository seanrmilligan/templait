package com.seanrmilligan.sitebuilder.view.gui;

import com.seanrmilligan.utils.StartupAction;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.seanrmilligan.sitebuilder.view.gui.Constants.MIN_HEIGHT;
import static com.seanrmilligan.sitebuilder.view.gui.Constants.MIN_WIDTH;

/**
 * Created by sean on 6/6/17.
 */
public class StartWindow extends Stage {
    StartupAction action;
    Scene scene;
    GridPane pane;
    Label appTitle;
    Button loadButton;
    Button newButton;

    public StartWindow(Stage owner) {
    	this.initOwner(owner);
    	this.initModality(Modality.WINDOW_MODAL);
    	
        this.pane = new GridPane();
        this.appTitle = new Label("Site Builder");
        this.loadButton = new Button("Load Site");
        this.newButton = new Button("New Site");
        
        this.appTitle.setFont(new Font(15));

        this.action = StartupAction.NO_ACTION;

        this.newButton.setOnAction(e -> {
            this.action = StartupAction.NEW;
            this.hide();
        });

        this.loadButton.setOnAction(e -> {
            this.action = StartupAction.LOAD;
            this.hide();
        });
        
        this.pane.add(this.appTitle, 0, 0, 2, 1);
        this.pane.add(this.newButton, 0, 1, 1, 1);
        this.pane.add(this.loadButton, 1, 1, 1, 1);
		this.pane.setPadding(new Insets(10));
		this.pane.setHgap(5);
		this.pane.setVgap(5);

		this.pane.setMinWidth(MIN_WIDTH);
		this.pane.setMinHeight(MIN_HEIGHT);
        
        this.scene = new Scene(this.pane);
        this.setScene(this.scene);
    }
    
    public void setTitleBar (String title) {
    	this.setTitle(title);
	}
	
	public void setButtonText (String newSiteButtonText, String loadSiteButtonText) {
    	this.newButton.setText(newSiteButtonText);
    	this.loadButton.setText(loadSiteButtonText);
	}

    public StartupAction getAction() {
        return this.action;
    }
}
