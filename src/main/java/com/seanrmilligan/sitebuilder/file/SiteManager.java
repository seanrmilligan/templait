package com.seanrmilligan.sitebuilder.controller;

import com.seanrmilligan.sitebuilder.model.Site;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import static com.seanrmilligan.sitebuilder.view.Strings.*;

/**
 * Created by sean on 6/6/17.
 */
public class SiteManager {
	public static final String SITE_BUILDER_DIRECTORY = ".sb";
	public static final String SITE_BUILDER_DATA = "project.json";
	
	public static final String JSON_KEY_SITE_NAME = "siteName";
	public static final String JSON_KEY_SITE_DOMAIN = "siteDomain";
	public static final String JSON_KEY_SITE_SUBDOMAINS = "subdomains";
	
	public static void newSite(File projDir, Site site) throws NullPointerException, IOException {
		File sbDir = constructFileFromPath(projDir.getAbsolutePath(), SiteManager.SITE_BUILDER_DIRECTORY);
		File sbFile = constructFileFromPath(sbDir.getAbsolutePath(), SITE_BUILDER_DATA);
		
		if (sbDir.exists()) {
			if (sbDir.isDirectory()) {
				throw new FileAlreadyExistsException(DIR_ALREADY_EXISTS + sbDir.getAbsolutePath());
			} else {
				throw new FileAlreadyExistsException(DIR_NAME_COLLISION + sbDir.getAbsolutePath());
			}
		}
		
		if (!sbDir.mkdir()) {
			throw new FileNotFoundException(DIR_CREATION_ERROR + sbDir.getAbsolutePath());
		}
		
		if (!sbFile.createNewFile()) {
			throw new FileNotFoundException(FILE_CREATION_ERROR + sbFile.getAbsolutePath());
		}
		
		saveSite(projDir, site);
	}
	
	public static Site loadSite(File projDir) throws NullPointerException, IOException {
		File sbFile = getSBFile(projDir);
		FileInputStream stream = new FileInputStream(sbFile);
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
		File sbFile = getSBFile(projDir);
		FileOutputStream stream = new FileOutputStream(sbFile);
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
			throw new NullPointerException(DIR_NULL);
		} else if (!projDir.isDirectory()) {
			throw new NotDirectoryException(NOT_A_DIR + projDir.getAbsolutePath());
		} else {
			String projPath = projDir.getAbsolutePath();
			File sbDir = constructFileFromPath(projPath, SiteManager.SITE_BUILDER_DIRECTORY);
			
			if (!sbDir.exists()) {
				throw new FileNotFoundException(DIR_DNE + sbDir.getAbsolutePath());
			}
			
			return sbDir;
		}
	}
	
	private static File getSBFile(File projDir) throws FileNotFoundException, NotDirectoryException {
		// getSBDir validates projDir and throws the appropriate exceptions
		String sbPath = getSBDir(projDir).getAbsolutePath();
		File sbFile = constructFileFromPath(sbPath, SiteManager.SITE_BUILDER_DATA);
		
		if (!sbFile.exists()) {
			throw new FileNotFoundException(FILE_DNE + sbFile.getAbsolutePath());
		}
		
		return sbFile;
	}
	
	public static File constructFileFromPath(String path, String filename) {
		File file;
		
		if (path.endsWith(File.separator)) {
			file = new File(path + filename);
		} else {
			file = new File(path + File.separator + filename);
		}
		
		return file;
	}
}
