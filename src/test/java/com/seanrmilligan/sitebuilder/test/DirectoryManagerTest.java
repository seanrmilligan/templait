package com.seanrmilligan.sitebuilder.test;


import com.seanrmilligan.sitebuilder.controller.DirectoryManager;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by sean on 6/24/17.
 */
public class DirectoryManagerTest {
	@Test
	public void shouldBuildTree() {
		File rootDirectory = new File("src/test/resources/DirectoryManager/");
		File rootFileOne = new File("src/test/resources/DirectoryManager/one.txt");
		File rootFileTwo = new File("src/test/resources/DirectoryManager/two.txt");
		File subDir = new File("src/test/resources/DirectoryManager/subOne/");
		File subDirFileOne = new File("src/test/resources/DirectoryManager/subOne/one.txt");
		File subDirFileTwo = new File("src/test/resources/DirectoryManager/subOne/two.txt");
		
		TreeItem<File> actualTree = DirectoryManager.getTree(rootDirectory);
		TreeItem<File> expectedTree = new TreeItem<>(rootDirectory);
		TreeItem<File> subDirTree = new TreeItem<>(subDir);
		subDirTree.getChildren().addAll(new TreeItem<>(subDirFileOne), new TreeItem<>(subDirFileTwo));
		expectedTree.getChildren().addAll(subDirTree, new TreeItem<>(rootFileOne), new TreeItem<>(rootFileTwo));
		
		
		assertTreeEquals(expectedTree, actualTree);
	}
	
	private static void assertTreeEquals(TreeItem<File> expectedTree, TreeItem<File> actualTree) {
		ObservableList<TreeItem<File>> expectedChildren = expectedTree.getChildren();
		ObservableList<TreeItem<File>> actualChildren = actualTree.getChildren();
		
		assertEquals(expectedTree.getValue(), actualTree.getValue());
		assertEquals(expectedChildren.size(), actualChildren.size());
		
		for (int i=0; i<expectedChildren.size(); i++) {
			assertTreeEquals(expectedChildren.get(i), actualChildren.get(i));
		}
	}
}
