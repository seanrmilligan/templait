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
	
	public static void newSite(File projDir, Site site) throws NullPointerException, IOException {
		File sbDir = constructFileFromPath(projDir.getAbsolutePath(), SiteManager.PROJECT_DIRECTORY);
		
		if (sbDir.exists()) {
			throw new FileAlreadyExistsException("Cannot make directory: " + PROJECT_DIRECTORY + " already exists.");
		}
		
		if (!sbDir.mkdir()) {
			throw new FileNotFoundException("Site Builder directory not made: " + sbDir.getAbsolutePath());
		}
		
		saveSite(projDir, site);
	}
	
	public static Site loadSite(File projDir) throws NullPointerException, IOException {
		File projFile = getProjFile(projDir);
		FileInputStream stream = new FileInputStream(projFile);
		JSONTokener parser = new JSONTokener(stream);
		JSONObject project = (JSONObject) parser.nextValue();
		Site site = new Site(project.getString(JSON_KEY_SITE_NAME), project.getString(JSON_KEY_SITE_DOMAIN));
		
		if (project.has(JSON_KEY_SITE_SUBDOMAINS)) {
			JSONArray subdomains = project.getJSONArray(JSON_KEY_SITE_SUBDOMAINS);
			
			for (int i=0; i<subdomains.length(); i++) {
				site.addSubdomain(subdomains.getString(i));
			}
		}
		
		stream.close();
		
		return site;
	}
	
	public static void saveSite(File projDir, Site site) throws IOException {
		File projFile = getProjFile(projDir);
		FileOutputStream stream = new FileOutputStream(projFile);
		JSONObject project = new JSONObject();
		JSONArray subdomains = new JSONArray();
		
		project.put(JSON_KEY_SITE_NAME, site.getName());
		project.put(JSON_KEY_SITE_DOMAIN, site.getDomain());
		
		for (String subdomain : site.getSubdomains()) {
			subdomains.put(subdomain);
		}
		
		project.put(JSON_KEY_SITE_SUBDOMAINS, subdomains);
		
		stream.write(project.toString().getBytes());
		
		stream.close();
	}
	
	private static File getSBDir(File projDir) throws FileNotFoundException, NotDirectoryException {
		if (projDir == null) {
			throw new NullPointerException("Project directory is null.");
		} else if (!projDir.isDirectory()) {
			throw new NotDirectoryException("Path is not a directory: " + projDir.getAbsolutePath());
		} else {
			String projPath = projDir.getAbsolutePath();
			File sbDir = constructFileFromPath(projPath, SiteManager.PROJECT_DIRECTORY);
			
			if (!sbDir.exists()) {
				throw new FileNotFoundException("Site Builder directory not found: " + sbDir.getAbsolutePath());
			}
			
			return sbDir;
		}
	}
	
	private static File getProjFile(File projDir) throws FileNotFoundException, NotDirectoryException {
		// getSBDir validates projDir and throws the appropriate exceptions
		String sbPath = getSBDir(projDir).getAbsolutePath();;
		File projFile = constructFileFromPath(sbPath, SiteManager.PROJECT_DATA);;
		
		if (!projFile.exists()) {
			throw new FileNotFoundException("File not found: " + projFile.getAbsolutePath());
		}
		
		return projFile;
	}
	
	private static File constructFileFromPath(String path, String filename) {
		File file;
		
		if (path.endsWith(File.separator)) {
			file = new File(path + filename);
		} else {
			file = new File(path + File.separator + filename);
		}
		
		return file;
	}
}
