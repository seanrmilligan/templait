package com.seanrmilligan.sitebuilder.view.gui;

import com.seanrmilligan.sitebuilder.controller.FileManager;
import com.seanrmilligan.sitebuilder.view.FileView;
import com.seanrmilligan.sitebuilder.view.SiteView;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.util.HashMap;

import static com.seanrmilligan.sitebuilder.view.Strings.APPLICATION_NAME;

public class SiteBuilderWindow implements SiteView, FileView {
	Stage primaryStage;
	Scene scene;
	BorderPane pane;
	MenuBar menus;
	Menu fileMenu;
	MenuItem newSiteMenuItem;
	MenuItem openSiteMenuItem;
	MenuItem closeSiteMenuItem;
	MenuItem quitProgMenuItem;
	Menu editMenu;
	MenuItem editSiteMenuItem;
	BorderPane body;
	HBox infoBar;
	Label siteNameLabel;
	Label siteName;
	Label siteDomainLabel;
	Label siteDomain;
	TreeView<File> directoryTree;
	String pathPrefix;
	String pathReplacement;
	TabPane editorTabs;
	HashMap<File, Tab> openFiles;
	
	public SiteBuilderWindow (Stage primaryStage){
		this.primaryStage = primaryStage;
		this.pane = new BorderPane();

		this.menus = new MenuBar();
		this.fileMenu = new Menu("_File");
		this.fileMenu.setMnemonicParsing(true);

		this.newSiteMenuItem = new MenuItem("_New Site");
		this.newSiteMenuItem.setMnemonicParsing(true);
		this.openSiteMenuItem = new MenuItem("_Open Site");
		this.openSiteMenuItem.setMnemonicParsing(true);
		this.closeSiteMenuItem = new MenuItem("_Close Site");
		this.closeSiteMenuItem.setMnemonicParsing(true);
		this.quitProgMenuItem = new MenuItem("_Quit");
		this.quitProgMenuItem.setMnemonicParsing(true);

		this.fileMenu.getItems().addAll(
			this.newSiteMenuItem,
			this.openSiteMenuItem,
			this.closeSiteMenuItem,
			this.quitProgMenuItem
		);
		
		this.editMenu = new Menu("_Edit");
		this.editMenu.setMnemonicParsing(true);
		
		this.editSiteMenuItem = new MenuItem("S_ite");
		this.editSiteMenuItem.setMnemonicParsing(true);
		
		this.editMenu.getItems().addAll(
			this.editSiteMenuItem
		);

		this.menus.getMenus().addAll(
			this.fileMenu,
			this.editMenu
		);

		this.pane.setTop(this.menus);
		
		this.body = new BorderPane();
		this.infoBar = new HBox();
		
		this.siteNameLabel = new Label("Site Name: ");
		this.siteName = new Label("");
		this.siteDomainLabel = new Label ("Site Domain: ");
		this.siteDomain = new Label("");
		
		this.infoBar.getChildren().addAll(
			this.siteNameLabel,
			this.siteName,
			this.siteDomainLabel,
			this.siteDomain
		);
		
		this.body.setTop(this.infoBar);
		
		this.directoryTree = new TreeView<>();
		this.pathPrefix = "";
		this.updateCellFactory();
		
		this.body.setLeft(this.directoryTree);
		
		this.editorTabs = new TabPane();
		this.openFiles = new HashMap<>();
		
		this.body.setCenter(this.editorTabs);
		
		this.body.setPadding(new Insets(10));

		this.pane.setCenter(this.body);

		this.scene = new Scene(this.pane);
		this.primaryStage.setScene(this.scene);
	}

	public void init(String windowTitle) {
		this.primaryStage.setTitle(windowTitle);
		this.primaryStage.show();
	}

	public void setSiteName(String name) {
		this.siteName.setText(name);
		this.primaryStage.setTitle(APPLICATION_NAME + " | " + name);
	}

	public void setSiteDomain(String domain) {
		this.siteDomain.setText(domain);
	}
	
	public void setDirectoryTree(TreeItem<File> tree) { this.directoryTree.setRoot(tree); }
	
	public void setDirectoryTreePathTruncation(String pathPrefix, String replacement) {
		this.pathPrefix = pathPrefix;
		this.pathReplacement = replacement;
		this.updateCellFactory();
	}
	
	private void updateCellFactory() {
		this.directoryTree.setCellFactory(tree -> {
			TreeCell<File> cell = new TreeCell<File>() {
				@Override
				public void updateItem(File item, boolean empty) {
					super.updateItem(item, empty);
					
					if (empty) {
						setText(null);
					} else {
						if (SiteBuilderWindow.this.pathPrefix.isEmpty()) {
							// default to just showing the name
							setText(getItem().getName());
						} else if (SiteBuilderWindow.this.pathPrefix.equals("/")) {
							// show absolute path
							setText(getItem().getAbsolutePath());
						} else {
							// do some other truncations such as
							// /home/dir --> ~/
							// /path/to/proj --> proj/
							String absPath = getItem().getAbsolutePath();
							
							if (absPath.startsWith(SiteBuilderWindow.this.pathPrefix)) {
								setText(absPath.replaceFirst(
									SiteBuilderWindow.this.pathPrefix,
									SiteBuilderWindow.this.pathReplacement
								));
							}
						}
					}
				}
			};
			
			cell.setOnMouseClicked(event -> {
				if(event.getButton().equals(MouseButton.PRIMARY)){
					if(event.getClickCount() >= 2 && !cell.isEmpty()){
						System.out.println(cell.getItem().toString());
						FileManager.open(SiteBuilderWindow.this, cell.getTreeItem().getValue());
					}
				}
			});
			
			return cell ;
		});
	}

	public void addFile(File file, String text) {
		Tab newTab = new Tab();
		CodeArea editor = new CodeArea();
		editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));
		editor.replaceText(0,0,text);
		newTab.setText(file.getName());
		newTab.setContent(editor);
		this.openFiles.put(file, newTab);
		this.editorTabs.getTabs().add(newTab);
	}
}
