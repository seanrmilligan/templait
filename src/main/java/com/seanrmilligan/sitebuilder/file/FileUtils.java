package com.seanrmilligan.sitebuilder.file;

import java.io.File;

public class FileUtils {
	public static File constructFileFromPath(String path, String filename) {
		File file;
		
		if (path.endsWith(File.separator)) {
			file = new File(path + filename);
		} else {
			file = new File(path + File.separator + filename);
		}
		
		return file;
	}
	
	public static String getExtension(File file) {
		return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('.'));
	}
}
