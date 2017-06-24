package com.seanrmilligan.sitebuilder.controller;

import com.seanrmilligan.sitebuilder.model.Site;
import com.seanrmilligan.sitebuilder.view.SiteDataDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by sean on 6/6/17.
 */
public class SiteManager {
	private static final String projectDirName = ".sb";
	private static final String projectJsonFileName = "project.json";
	
	public static Site newSite(Stage primaryStage) {
		Site site;
		SiteDataDialog dialog = new SiteDataDialog(primaryStage);

		ArrayList<String> schemaList = new ArrayList<>();
		schemaList.add("http");
		schemaList.add("https");

		dialog.setSchemaList(schemaList);
		dialog.setSchema("http");
		dialog.setPort(80);
		dialog.init("Create a Site", "Create");
		dialog.showAndWait();
		
		site = new Site(dialog.getSiteName(), dialog.getSiteDomain());
		
		return site;
	}
	
	public static Site loadSite(Stage primaryStage) throws NullPointerException, FileNotFoundException {
		Site site = null;
		DirectoryChooser chooser = new DirectoryChooser();
		File homeDir = null, projDir = null, sbDir = null, projFile = null;
		FileInputStream stream;
		JSONObject project;

		// get the logged in user's home directory
		homeDir = new File(System.getProperty("user.home"));

		// set the initial directory as the logged in user's home directory
		chooser.setInitialDirectory(homeDir);

		// let the user traverse directories and select the directory that contains the project
		projDir = chooser.showDialog(primaryStage);

		// do not proceed if the user X'd out of the dialog
		if (projDir == null) {
			throw new NullPointerException("No directory selected.");
		}

		// search through the directory the user choose to see if the site builder settings directory is inside
		for (File item : projDir.listFiles()) {
			if (item.isDirectory() && item.getName().equals(SiteManager.projectDirName)) {
				sbDir = item;
				break;
			}
		}

		// no site builder settings directory found; this is not a site builder managed project
		if (sbDir == null) {
			throw new FileNotFoundException("Site Builder .sb directory not found.");
		}

		// get the project json file from in the site builder settings dir
		for (File item : sbDir.listFiles()) {
			if (item.isFile() && item.getName().equals(SiteManager.projectJsonFileName)) {
				projFile = item;
				break;
			}
		}
		
		if (projFile == null) {
			throw new FileNotFoundException("File not found: " + SiteManager.projectJsonFileName);
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
