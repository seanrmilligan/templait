package com.seanrmilligan.sitebuilder.test;

import static com.seanrmilligan.sitebuilder.file.FileUtils.constructFileFromPath;
import static com.seanrmilligan.sitebuilder.file.FileNames.SITE_BUILDER_DATA;
import static com.seanrmilligan.sitebuilder.file.FileNames.SITE_BUILDER_DIRECTORY;

import static com.seanrmilligan.sitebuilder.view.Strings.DIR_ALREADY_EXISTS;
import static com.seanrmilligan.sitebuilder.view.Strings.DIR_DNE;
import static com.seanrmilligan.sitebuilder.view.Strings.FILE_DNE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.seanrmilligan.sitebuilder.file.FileNames;
import com.seanrmilligan.sitebuilder.file.SiteManager;
import com.seanrmilligan.sitebuilder.model.Site;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class SiteManagerTest {
	
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	@Test
	public void newSite() {
		Site site = new Site("Example Site", "example.com");
		
		try {
			File projDir = temporaryFolder.newFolder();
			SiteManager.newSite(projDir, site);
			
			File sbDir = constructFileFromPath(projDir.getAbsolutePath(), FileNames.SITE_BUILDER_DIRECTORY);
			File sbFile = constructFileFromPath(sbDir.getAbsolutePath(), FileNames.SITE_BUILDER_DATA);
			
			assertEquals(true, sbDir.exists());
			assertEquals(true, sbFile.exists());
			assertEquals(true, site.equals(SiteManager.loadSite(projDir)));
		} catch (IOException e) {
			fail(e.toString());
		}
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
			assertEquals(FILE_DNE + sbFile.getAbsolutePath(), e.getMessage());
		}
	}
	
	@Test
	public void sbDirAlreadyExists() {
		File projDir = new File("src/test/resources/SiteManager/sb-dir-already-exists/");
		File sbDir = constructFileFromPath(projDir.getAbsolutePath(), SITE_BUILDER_DIRECTORY);
		
		try {
			SiteManager.newSite(projDir, new Site("Example Site", "example.com"));
			fail("IOException not thrown.");
		} catch (IOException e) {
			assertEquals(DIR_ALREADY_EXISTS + sbDir.getAbsolutePath(), e.getMessage());
		}
	}
	
	@Test
	public void sbDirMissing() {
		File projDir = new File("src/test/resources/SiteManager/sb-dir-missing/");
		File sbDir = constructFileFromPath(projDir.getAbsolutePath(), SITE_BUILDER_DIRECTORY);
		
		try {
			SiteManager.loadSite(projDir);
			fail("IOException not thrown.");
		} catch (IOException e) {
			assertEquals(DIR_DNE + sbDir.getAbsolutePath(), e.getMessage());
		}
	}
}
