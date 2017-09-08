package com.seanrmilligan.sitebuilder.controller;

import com.seanrmilligan.sitebuilder.file.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class FileController {
	FileManager fileManager;
	
	public FileController (FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	private void build(File file, HashMap<String, String> map) throws IOException {
		String templatePath = this.fileManager.getTemplateDir().getAbsolutePath();
		String buildPath = this.fileManager.getBuildDir().getAbsolutePath();
		File outFile = new File(file.getAbsolutePath().replaceFirst(templatePath, buildPath));
		TemplateBuilder.build(file, outFile, StandardCharsets.UTF_8, map);
	}
	
	public void buildAll() throws IOException {
		buildAll(this.fileManager.getTemplateDir(), MapManager.loadMap(this.fileManager.getMapFile(), StandardCharsets.UTF_8));
	}
	
	private void buildAll(File dir, HashMap<String, String> map) throws IOException {
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				build(file, map);
			} else if (file.isDirectory()) {
				buildAll(file, map);
			}
		}
	}
}
