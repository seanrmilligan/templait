package com.seanrmilligan.sitebuilder;

import com.seanrmilligan.sitebuilder.controller.SiteDataController;
import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.view.SiteBuilderWindow;

import java.util.Locale;

import com.seanrmilligan.sitebuilder.view.StartWindow;
import com.seanrmilligan.utils.Action;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.seanrmilligan.sitebuilder.view.Strings.APPLICATION_NAME;
import static com.seanrmilligan.sitebuilder.view.Strings.LOAD_SITE_BUTTON_TEXT;
import static com.seanrmilligan.sitebuilder.view.Strings.NEW_SITE_BUTTON_TEXT;

public class SiteBuilder extends Application {
	SiteBuilderWindow gui;

	/**
	 * @param primaryStage This application's window.
	 */
	@Override
	public void start(Stage primaryStage) {
		Site site = null;
		
		StartWindow launcherView = new StartWindow(primaryStage);
		launcherView.setTitleBar(APPLICATION_NAME);
		launcherView.setButtonText(NEW_SITE_BUTTON_TEXT, LOAD_SITE_BUTTON_TEXT);
		launcherView.showAndWait();
		Action action = launcherView.getAction();
		
		SiteDataController siteDataController = new SiteDataController();
		
		switch (action) {
			case NEW:
				site = siteDataController.newSite(primaryStage);
				break;
			case LOAD:
				try {
					site = siteDataController.loadSite(primaryStage);
				} catch (Exception e) {
					System.out.println(e.toString());
					System.out.println(e.getMessage());
				}
				break;
		}
		
		if (site != null) {
			this.gui = new SiteBuilderWindow(primaryStage);
			this.gui.setSiteName(site.getName());
			this.gui.setSiteDomain(site.getDomain());
			this.gui.init(APPLICATION_NAME);
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		launch(args);
	}
}
