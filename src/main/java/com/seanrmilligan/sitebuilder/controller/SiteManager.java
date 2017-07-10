package com.seanrmilligan.sitebuilder.controller;

import com.seanrmilligan.sitebuilder.model.Site;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by sean on 6/6/17.
 */
public class SiteManager {
	private static final String PROJECT_DIRECTORY = ".sb";
	private static final String PROJECT_DATA = "project.json";
	
	private static final String JSON_KEY_SITE_NAME = "siteName";
	private static final String JSON_KEY_SITE_DOMAIN = "siteDomain";
	private static final String JSON_KEY_SITE_SUBDOMAINS = "subdomains";
	
	public static void newSite(File projDir, Site site) throws NullPointerException, NotDirectoryException, FileAlreadyExistsException, FileNotFoundException, IOException {
		File sbDir, projFile;
		String projPath, sbPath;
		FileOutputStream stream;
		JSONObject project;
		JSONArray subdomains;
		
		if (projDir == null) {
			throw new NullPointerException("Project directory is null.");
		}
		
		projPath = projDir.getAbsolutePath();
		
		if (!projDir.isDirectory()) {
			throw new NotDirectoryException("Path is not a directory: " + projPath);
		}
		
		if (projPath.endsWith(File.separator)) {
			sbDir = new File(projPath + SiteManager.PROJECT_DIRECTORY);
		} else {
			sbDir = new File(projPath + File.separator + SiteManager.PROJECT_DIRECTORY);
		}
		
		sbPath = sbDir.getAbsolutePath();
		
		if (sbDir.exists()) {
			throw new FileAlreadyExistsException("Site Builder directory found: " + sbPath);
		}
		
		if (!sbDir.mkdir()) {
			throw new FileNotFoundException("Site Builder directory not made: " + sbPath);
		}
		
		projFile = new File(sbPath + File.separator + SiteManager.PROJECT_DATA);
		stream = new FileOutputStream(projFile);
		project = new JSONObject();
		subdomains = new JSONArray();
		
		project.put(JSON_KEY_SITE_NAME, site.getName());
		project.put(JSON_KEY_SITE_DOMAIN, site.getDomain());
		
		for (String subdomain : site.getSubdomains()) {
			subdomains.put(subdomain);
		}
		
		project.put(JSON_KEY_SITE_SUBDOMAINS, subdomains);
		
		stream.write(project.toString().getBytes());
		
		stream.close();
	}
	
	public static Site loadSite(File projDir) throws NullPointerException, FileNotFoundException, IOException {
		Site site = null;
		File sbDir = null, projFile = null;
		FileInputStream stream;
		JSONObject project;
		
		if (projDir == null) {
			throw new NullPointerException("Project directory is null.");
		}

		// search through the directory the user choose to see if the site builder settings directory is inside
		for (File item : projDir.listFiles()) {
			if (item.isDirectory() && item.getName().equals(SiteManager.PROJECT_DIRECTORY)) {
				sbDir = item;
				break;
			}
		}

		// no site builder settings directory found; this is not a site builder managed project
		if (sbDir == null) {
			throw new FileNotFoundException("Site Builder directory not found: " + PROJECT_DIRECTORY);
		}

		// get the project json file from in the site builder settings dir
		for (File item : sbDir.listFiles()) {
			if (item.isFile() && item.getName().equals(PROJECT_DATA)) {
				projFile = item;
				break;
			}
		}
		
		if (projFile == null) {
			throw new FileNotFoundException("File not found: " + PROJECT_DATA);
		}
		
		stream = new FileInputStream(projFile);
		JSONTokener parser = new JSONTokener(stream);
		project = (JSONObject) parser.nextValue();
		
		site = new Site(project.getString(JSON_KEY_SITE_NAME), project.getString(JSON_KEY_SITE_DOMAIN));
		
		if (project.has(JSON_KEY_SITE_SUBDOMAINS)) {
			JSONArray subdomains = project.getJSONArray(JSON_KEY_SITE_SUBDOMAINS);
			
			for (int i=0; i<subdomains.length(); i++) {
				site.addSubdomain(subdomains.getString(i));
			}
		}
		
		stream.close();
		
		return site;
	}
	
	public static void saveSite(File projDir, Site site) {
	
	}
}
