package com.seanrmilligan.sitebuilder.test;

import com.seanrmilligan.sitebuilder.controller.DirectoryManager;
import javafx.scene.control.TreeItem;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by sean on 6/24/17.
 */
public class DirectoryManagerTest {
	@Test
	public void shouldBuildTree() {
		File rootDirectory = new File("src/test/resources/directory-manager/");
		File rootFileOne = new File("src/test/resources/directory-manager/one.txt");
		File rootFileTwo = new File("src/test/resources/directory-manager/two.txt");
		File subDir = new File("src/test/resources/directory-manager/subOne/");
		File subDirFileOne = new File("src/test/resources/directory-manager/subOne/one.txt");
		File subDirFileTwo = new File("src/test/resources/directory-manager/subOne/two.txt");
		
		TreeItem<File> actualTree = DirectoryManager.getTree(rootDirectory);
		TreeItem<File> expectedTree = new TreeItem<>(rootDirectory);
		TreeItem<File> subDirTree = new TreeItem<>(subDir);
		subDirTree.getChildren().addAll(new TreeItem<>(subDirFileOne), new TreeItem<>(subDirFileTwo));
		expectedTree.getChildren().addAll(subDirTree, new TreeItem<>(rootFileOne), new TreeItem<>(rootFileTwo));
		
		assertEquals(expectedTree, actualTree);
	}
	
	private static void printTree(TreeItem<?> tree) {
		System.out.println(tree.toString());
		
		for (TreeItem<?> child : tree.getChildren()) {
			printTree(child);
		}
	}
}
