package com.seanrmilligan.sitebuilder.file;

import com.seanrmilligan.sitebuilder.view.MultiFileView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

/**
 * Created by sean on 6/25/17.
 */
public class FileManager {
	private HashSet<String> fileExtensions;
	private HashSet<File> openFiles;
	private File sbDir;
	private File includesDir;
	private File mapFile;
	private File projectFile;
	private File templateDir;
	private File buildDir;
	
	public FileManager(File projDir) {
		String projPath = projDir.getAbsolutePath();
		
		this.fileExtensions = new HashSet<>();
		this.openFiles = new HashSet<>();
		
		this.sbDir = FileUtils.constructFileFromPath(projPath, FileNames.SITE_BUILDER_DIRECTORY);
		this.includesDir = FileUtils.constructFileFromPath(sbDir.getAbsolutePath(), FileNames.SITE_BUILDER_INCLUDE_DIRECTORY);
		this.mapFile = FileUtils.constructFileFromPath(sbDir.getAbsolutePath(), FileNames.SITE_BUILDER_MAP);
		this.projectFile = FileUtils.constructFileFromPath(sbDir.getAbsolutePath(), FileNames.SITE_BUILDER_DATA);
		
		this.templateDir = FileUtils.constructFileFromPath(projPath, FileNames.TEMPLATE_DIRECTORY);
		
		this.buildDir = FileUtils.constructFileFromPath(projPath, FileNames.BUILD_DIRECTORY);
	}
	
	public boolean open(MultiFileView view, File file) {
		boolean fileOpened = true;
		
		if (file.isFile() && !this.openFiles.contains(file) && this.accepts(file)) {
			try {
				List<String> lines = org.apache.commons.io.FileUtils.readLines(file, StandardCharsets.UTF_8);
				view.addFile(file.getName(), String.join("\n", lines));
				this.openFiles.add(file);
			} catch (IOException e) {
				fileOpened = false;
			}
		}
		
		return fileOpened;
	}
	
	public void addFileExtension(String fileExt) {
		this.fileExtensions.add(fileExt);
	}
	
	public boolean accepts(String fileExt) {
		return this.fileExtensions.isEmpty() ? true : this.fileExtensions.contains(fileExt);
	}
	
	public boolean accepts(File file) {
		return this.accepts(FileUtils.getExtension(file));
	}
	
	public File getSBDir() {
		return this.sbDir;
	}
	
	public File getIncludesDir() {
		return this.includesDir;
	}
	
	public File getMapFile() {
		return this.mapFile;
	}
	
	public File getProjectFile() {
		return this.projectFile;
	}
	
	public File getTemplateDir() {
		return this.templateDir;
	}
	
	public File getBuildDir() {
		return this.buildDir;
	}
}
