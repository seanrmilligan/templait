package com.seanrmilligan.sitebuilder;

import com.seanrmilligan.sitebuilder.controller.FileController;
import com.seanrmilligan.sitebuilder.file.DirectoryManager;
import com.seanrmilligan.sitebuilder.file.FileManager;
import com.seanrmilligan.sitebuilder.file.SiteManager;
import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.view.gui.SiteBuilderWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import com.seanrmilligan.sitebuilder.view.gui.SiteDataDialog;
import com.seanrmilligan.sitebuilder.view.gui.StartWindow;
import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import static com.seanrmilligan.sitebuilder.view.Strings.APPLICATION_NAME;
import static com.seanrmilligan.sitebuilder.view.Strings.LOAD_SITE_BUTTON_TEXT;
import static com.seanrmilligan.sitebuilder.view.Strings.NEW_SITE_BUTTON_TEXT;

public class SiteBuilder extends Application {
	private SiteBuilderWindow gui;

	/**
	 * @param primaryStage This application's window.
	 */
	@Override
	public void start(Stage primaryStage) {
		File projDir;
		Site site;
		
		StartWindow launcherView = new StartWindow(primaryStage);
		launcherView.setTitleBar(APPLICATION_NAME);
		launcherView.setButtonText(NEW_SITE_BUTTON_TEXT, LOAD_SITE_BUTTON_TEXT);
		launcherView.showAndWait();
		
		switch (launcherView.getAction()) {
			case NEW:
				projDir = this.getProjDir(primaryStage);
				site = this.newSite(primaryStage, projDir);
				this.initWindow(primaryStage, projDir, site);
				break;
			case LOAD:
				projDir = this.getProjDir(primaryStage);
				site = this.loadSite(projDir);
				this.initWindow(primaryStage, projDir, site);
				break;
			default:
				System.exit(0);
		}
	}
	
	private File getProjDir(Stage primaryStage) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		return chooser.showDialog(primaryStage);
	}
	
	private void initWindow(Stage primaryStage, File projDir, Site site) {
		if (site != null) {
			FileManager fileManager = new FileManager(projDir);
			this.gui = new SiteBuilderWindow(primaryStage);
			this.gui.setTitleBar(APPLICATION_NAME + " | " + site.getName());
			this.gui.setDirectoryTree(DirectoryManager.getTree(projDir));
			this.gui.setFileController(new FileController(fileManager));
			this.gui.setFileManager(fileManager);
			this.gui.setMaximized(true);
			this.gui.show();
		}
	}
	
	public void setPathsRelativeToHome(File homeDir) {
		this.gui.setDirectoryTreePathTruncation(homeDir.getAbsolutePath(), "~");
	}
	
	public void setPathsRelativeToProject(File projDir) {
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
	
	private Site newSite(Stage primaryStage, File projDir) {
		SiteDataDialog dialog = new SiteDataDialog(primaryStage);
		Site site;
		
		ArrayList<String> schemaList = new ArrayList<>();
		schemaList.add("http");
		schemaList.add("https");
		
		dialog.setSchemaList(schemaList);
		dialog.setSchema("http");
		dialog.setPort(80);
		dialog.init("Create a Site", "Create");
		dialog.showAndWait();
		
		site = new Site(dialog.getSiteName(), dialog.getSiteDomain());
		
		try {
			SiteManager.newSite(projDir, site);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return site;
	}
	
	private Site loadSite(File projDir) {
		try {
			return SiteManager.loadSite(projDir);
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
}
