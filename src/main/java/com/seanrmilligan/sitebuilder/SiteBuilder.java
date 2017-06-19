package com.seanrmilligan.sitebuilder;

import com.seanrmilligan.sitebuilder.controller.SiteDataController;
import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.view.SiteBuilderWindow;

import java.util.Locale;

import com.seanrmilligan.sitebuilder.view.StartWindow;
import com.seanrmilligan.utils.Action;
import javafx.application.Application;
import javafx.stage.Stage;

public class SiteBuilder extends Application {
	SiteBuilderWindow gui;
	SiteDataController siteDataController;

	/**
	 * @param primaryStage This application's window.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.siteDataController = new SiteDataController();
		Site site = this.startup(primaryStage);

		if (site != null) {
			this.gui = new SiteBuilderWindow(primaryStage);
			this.gui.setSiteName(site.getName());
			this.gui.setSiteDomain(site.getDomain());
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
				site = this.siteDataController.newSite(primaryStage);
				break;
			case LOAD:
				try {
					site = this.siteDataController.loadSite(primaryStage);
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				break;
			default:
				System.err.println("Invalid action: " + action.toString());
		}
		
		return site;
	}
}
