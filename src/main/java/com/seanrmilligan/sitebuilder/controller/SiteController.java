package com.seanrmilligan.sitebuilder.controller;

import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.gui.SiteDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by sean on 6/6/17.
 */
public class SiteController {
	private static final String projectDirName = ".sb";
	private static final String projectJsonFileName = "project.json";
	
	public Site newSite(Stage primaryStage) {
		Site site;
		SiteDialog dialog = new SiteDialog(primaryStage);
		
		dialog.init("Create a Site", "Create");
		dialog.showAndWait();
		
		site = new Site(dialog.getSiteName(), dialog.getSiteDomain());
		
		return site;
	}
	
	public Site loadSite(Stage primaryStage) throws NullPointerException, FileNotFoundException, IOException {
		Site site = null;
		DirectoryChooser chooser = new DirectoryChooser();
		File projDir = null, sbDir = null, projFile = null;
		FileInputStream stream;
		JSONObject project;
		
		projDir = chooser.showDialog(primaryStage);
		
		if (projDir == null) {
			throw new NullPointerException("No directory selected.");
		}
		
		for (File item : projDir.listFiles()) {
			if (item.isDirectory() && item.getName().equals(SiteController.projectDirName)) {
				sbDir = item;
				break;
			}
		}
		
		if (sbDir == null) {
			throw new FileNotFoundException("Site Builder .sb directory not found.");
		}
		
		for (File item : sbDir.listFiles()) {
			if (item.isFile() && item.getName().equals(SiteController.projectJsonFileName)) {
				projFile = item;
				break;
			}
		}
		
		if (projFile == null) {
			throw new FileNotFoundException("File not found: " + SiteController.projectJsonFileName);
		}
		
		stream = new FileInputStream(projFile);
		JSONTokener parser = new JSONTokener(stream);
		project = (JSONObject) parser.nextValue();
		
		site = new Site(project.getString("siteName"), project.getString("siteDomain"));
		
		if (project.has("subdomains")) {
			JSONArray subdomains = project.getJSONArray("subdomains");
			
			for (int i=0; i<subdomains.length(); i++) {
				site.addSubdomain(subdomains.getString(i));
			}
		}
		
		return site;
	}
	
	public boolean hasProject(File directory) {
		for (File content : directory.listFiles()) {
			if (content.isDirectory() && content.getName().equals(projectDirName)) {
				return true;
			}
		}
		
		return false;
	}
}
