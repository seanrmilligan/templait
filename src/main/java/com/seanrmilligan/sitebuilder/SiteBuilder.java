package com.seanrmilligan.sitebuilder;

import com.seanrmilligan.sitebuilder.controller.SiteController;
import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.gui.SiteBuilderWindow;

import java.util.Locale;

import com.seanrmilligan.sitebuilder.gui.StartWindow;
import com.seanrmilligan.utils.Action;
import com.seanrmilligan.utils.ActionableController;
import javafx.application.Application;
import javafx.stage.Stage;

public class SiteBuilder extends Application {
	SiteBuilderWindow gui;
	SiteController siteController;


	/**
	 * @param primaryStage This application's window.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.siteController = new SiteController();
		Site site = this.startup(primaryStage);
		
		if (site != null) {
			this.gui = new SiteBuilderWindow(primaryStage);
			this.gui.init("Site Builder");
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		launch(args);
	}

    public Site startup(Stage primaryStage) {
		Site site = null;
        StartWindow launcherView = new StartWindow(primaryStage);
        launcherView.init("Site Builder");
		launcherView.showAndWait();
		Action action = launcherView.getAction();
		
		switch (action) {
			case NEW:
				site = this.siteController.newSite(primaryStage);
				break;
			case LOAD:
				try {
					site = this.siteController.loadSite(primaryStage);
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				break;
		}
		
		return site;
	}
}
