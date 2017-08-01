package com.seanrmilligan.sitebuilder.test;

import static com.seanrmilligan.sitebuilder.controller.SiteManager.constructFileFromPath;
import static com.seanrmilligan.sitebuilder.controller.SiteManager.SITE_BUILDER_DATA;
import static com.seanrmilligan.sitebuilder.controller.SiteManager.SITE_BUILDER_DIRECTORY;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.seanrmilligan.sitebuilder.controller.SiteManager;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class SiteManagerTest {
	
	@Test
	public void projFileAlreadyExists() {
	
	}
	
	@Test
	public void projFileMissing() {
		File projDir = new File("src/test/resources/SiteManager/proj-file-missing/");
		File sbDir = constructFileFromPath(projDir.getAbsolutePath(), SITE_BUILDER_DIRECTORY);
		File sbFile = constructFileFromPath(sbDir.getAbsolutePath(), SITE_BUILDER_DATA);
		
		try {
			SiteManager.loadSite(projDir);
			fail("IOException not thrown.");
		} catch (IOException e) {
			assertEquals("File not found: " + sbFile.getAbsolutePath(), e.getMessage());
		}
	}
	
	@Test
	public void sbDirAlreadyExists() {
	
	}
	
	@Test
	public void sbDirMissing() {
		File projDir = new File("src/test/resources/SiteManager/sb-dir-missing/");
		File sbDir = constructFileFromPath(projDir.getAbsolutePath(), SITE_BUILDER_DIRECTORY);
		
		try {
			SiteManager.loadSite(projDir);
			fail("IOException not thrown.");
		} catch (IOException e) {
			assertEquals("Site Builder directory not found: " + sbDir.getAbsolutePath(), e.getMessage());
		}
	}
}
