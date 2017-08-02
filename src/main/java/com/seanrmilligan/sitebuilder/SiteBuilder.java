package com.seanrmilligan.sitebuilder;

import com.seanrmilligan.sitebuilder.controller.DirectoryManager;
import com.seanrmilligan.sitebuilder.controller.FileManager;
import com.seanrmilligan.sitebuilder.file.SiteManager;
import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.view.gui.SiteBuilderWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import com.seanrmilligan.sitebuilder.view.gui.SiteDataDialog;
import com.seanrmilligan.sitebuilder.view.gui.StartWindow;
import com.seanrmilligan.utils.StartupAction;
import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import static com.seanrmilligan.sitebuilder.view.Strings.APPLICATION_NAME;
import static com.seanrmilligan.sitebuilder.view.Strings.LOAD_SITE_BUTTON_TEXT;
import static com.seanrmilligan.sitebuilder.view.Strings.NEW_SITE_BUTTON_TEXT;

public class SiteBuilder extends Application {
	Site site;
	SiteBuilderWindow gui;
	File homeDir, projDir;
	
	FileManager files;

	/**
	 * @param primaryStage This application's window.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.site = null;
		
		StartWindow launcherView = new StartWindow(primaryStage);
		launcherView.setTitleBar(APPLICATION_NAME);
		launcherView.setButtonText(NEW_SITE_BUTTON_TEXT, LOAD_SITE_BUTTON_TEXT);
		launcherView.showAndWait();
		StartupAction action = launcherView.getAction();
		
		switch (action) {
			case NEW:
				this.getProjDir(primaryStage);
				this.newSite(primaryStage);
				this.files = new FileManager();
				this.initWindow(primaryStage);
				break;
			case LOAD:
				this.getProjDir(primaryStage);
				this.loadSite();
				this.files = new FileManager();
				this.initWindow(primaryStage);
				break;
			default:
				System.exit(0);
		}
	}
	
	public void getProjDir(Stage primaryStage) {
		DirectoryChooser chooser = new DirectoryChooser();
		this.homeDir = new File(System.getProperty("user.home"));
		chooser.setInitialDirectory(this.homeDir);
		this.projDir = chooser.showDialog(primaryStage);
	}
	
	public void initWindow(Stage primaryStage) {
		if (this.site != null) {
			this.gui = new SiteBuilderWindow(primaryStage);
			this.gui.setTitleBar(APPLICATION_NAME + " | " + site.getName());
			this.gui.setDirectoryTree(DirectoryManager.getTree(projDir));
			this.gui.setFileManager(this.files);
			this.gui.setMaximized(true);
			this.gui.show();
		}
	}
	
	public void setPathsRelativeToHome() {
		this.gui.setDirectoryTreePathTruncation(this.homeDir.getAbsolutePath(), "~");
	}
	
	public void setPathsRelativeToProject() {
		this.gui.setDirectoryTreePathTruncation(projDir.getAbsolutePath(), projDir.getName());
	}
	
	public void setPathsToFileName() {
		this.gui.setDirectoryTreePathTruncation("","");
	}
	
	public void setPathsToAbsolute() {
		this.gui.setDirectoryTreePathTruncation("/","");
	}
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		launch(args);
	}
	
	private void newSite(Stage primaryStage) {
		SiteDataDialog dialog = new SiteDataDialog(primaryStage);
		
		ArrayList<String> schemaList = new ArrayList<>();
		schemaList.add("http");
		schemaList.add("https");
		
		dialog.setSchemaList(schemaList);
		dialog.setSchema("http");
		dialog.setPort(80);
		dialog.init("Create a Site", "Create");
		dialog.showAndWait();
		
		this.site = new Site(dialog.getSiteName(), dialog.getSiteDomain());
		
		try {
			SiteManager.newSite(this.projDir, this.site);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void loadSite() {
		try {
			this.site = SiteManager.loadSite(this.projDir);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
