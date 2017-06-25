package com.seanrmilligan.sitebuilder;

import com.seanrmilligan.sitebuilder.controller.DirectoryManager;
import com.seanrmilligan.sitebuilder.controller.SiteManager;
import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.view.gui.SiteBuilderWindow;

import java.io.File;
import java.util.Locale;

import com.seanrmilligan.sitebuilder.view.gui.StartWindow;
import com.seanrmilligan.utils.Action;
import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import static com.seanrmilligan.sitebuilder.view.Strings.APPLICATION_NAME;
import static com.seanrmilligan.sitebuilder.view.Strings.LOAD_SITE_BUTTON_TEXT;
import static com.seanrmilligan.sitebuilder.view.Strings.NEW_SITE_BUTTON_TEXT;
import static com.seanrmilligan.utils.Action.LOAD;
import static com.seanrmilligan.utils.Action.NEW;

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
		
		if (action == NEW || action == LOAD) {
			DirectoryChooser chooser = new DirectoryChooser();
			File homeDir, projDir;
			homeDir = new File(System.getProperty("user.home"));
			chooser.setInitialDirectory(homeDir);
			projDir = chooser.showDialog(primaryStage);
			
			if (action == NEW) {
				site = SiteManager.newSite(primaryStage);
			} else {
				try {
					site = SiteManager.loadSite(projDir);
				} catch (Exception e) {
					System.out.println(projDir.toString());
					System.out.println(e.toString());
					System.out.println(e.getMessage());
				}
			}
			
			if (site != null) {
				this.gui = new SiteBuilderWindow(primaryStage);
				this.gui.setSiteName(site.getName());
				this.gui.setSiteDomain(site.getDomain());
				this.gui.setDirectoryTree(DirectoryManager.getTree(projDir));
				//this.gui.setDirectoryTreePathTruncation(homeDir.getAbsolutePath(), "~");
				this.gui.setDirectoryTreePathTruncation(projDir.getAbsolutePath(), projDir.getName());
				this.gui.init(APPLICATION_NAME);
			}
		}
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		launch(args);
	}
}
