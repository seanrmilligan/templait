package com.seanrmilligan.sitebuilder.controller;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sean on 6/24/17.
 */
public class DirectoryManager {
	
	public static TreeItem<File> getTree(File rootDir) {
		TreeItem<File> rootNode = new TreeItem<>(rootDir);
		List<TreeItem<File>> dirs = new ArrayList<>();
		List<TreeItem<File>> files = new ArrayList<>();
		Comparator<TreeItem<File>> nameComparator = new FileComparator();
		
		for (File item : rootDir.listFiles()) {
			if (item.isDirectory()) {
				dirs.add(getTree(item));
			} else {
				files.add(new TreeItem<>(item));
			}
		}
		
		Collections.sort(dirs, nameComparator);
		Collections.sort(files, nameComparator);
		
		rootNode.getChildren().addAll(dirs);
		rootNode.getChildren().addAll(files);
		
		return rootNode;
	}
}

class FileComparator implements Comparator<TreeItem<File>> {
	@Override
	public int compare(TreeItem<File> a, TreeItem<File> b) {
		return a.getValue().getName().compareTo(b.getValue().getName());
	}
}
