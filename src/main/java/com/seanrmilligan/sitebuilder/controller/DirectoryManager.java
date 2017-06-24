package com.seanrmilligan.sitebuilder.controller;

import javafx.scene.control.TreeItem;

import java.io.File;

/**
 * Created by sean on 6/24/17.
 */
public class DirectoryManager {
	
	public static TreeItem<File> getTree(File rootDir) {
		TreeItem<File> rootNode = new TreeItem<>(rootDir);
		
		for (File item : rootDir.listFiles()) {
			TreeItem<File> node;
			
			if (item.isDirectory()) {
				node = getTree(item);
			} else {
				node = new TreeItem<>(item);
			}
			
			rootNode.getChildren().add(node);
		}
		
		return rootNode;
	}
	
	private class DirectoryWatcher {
	
	}
}
