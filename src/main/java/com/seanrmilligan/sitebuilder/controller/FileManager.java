package com.seanrmilligan.sitebuilder.controller;

import com.seanrmilligan.sitebuilder.view.MultiFileView;
import org.apache.commons.io.FileUtils;

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
	
	public FileManager() {
		this.fileExtensions = new HashSet<>();
		this.openFiles = new HashSet<>();
	}
	
	public boolean open(MultiFileView view, File file) {
		boolean fileOpened = true;
		
		if (file.isFile() && !this.openFiles.contains(file) && this.accepts(file)) {
			try {
				List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
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
		return this.accepts(getExtension(file));
	}
	
	public String getExtension(File file) {
		return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('.'));
	}
}
