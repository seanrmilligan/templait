package com.seanrmilligan.sitebuilder.controller;

import com.seanrmilligan.sitebuilder.view.FileView;
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
	private static HashSet<File> openFiles = new HashSet<>();
	
	public static boolean open(FileView view, File file) {
		boolean fileOpened = true;
		
		if (file.isFile() && !openFiles.contains(file)) {
			try {
				List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
				view.addFile(file, String.join("\n", lines));
				openFiles.add(file);
			} catch (IOException e) {
				fileOpened = false;
			}
		}
		
		return fileOpened;
	}
}
