package com.seanrmilligan.sitebuilder.view;

import com.seanrmilligan.sitebuilder.model.SiteBuilderView;
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

public class SiteBuilderWindow implements SiteBuilderView {
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
	CodeArea editor;
	
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
		
		this.editor = new CodeArea();
		this.editor.setParagraphGraphicFactory(LineNumberFactory.get(this.editor));
		
		this.body.setCenter(this.editor);
		
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
						if (!SiteBuilderWindow.this.pathPrefix.isEmpty() &&
							getItem().getAbsolutePath().startsWith(SiteBuilderWindow.this.pathPrefix)) {
							setText(getItem().getAbsolutePath().replaceFirst(
								SiteBuilderWindow.this.pathPrefix,
								SiteBuilderWindow.this.pathReplacement
							));
						}
					}
				}
			};
			
			cell.setOnMouseClicked(event -> {
				if(event.getButton().equals(MouseButton.PRIMARY)){
					if(event.getClickCount() >= 2 && !cell.isEmpty()){
						TreeItem<File> treeItem = cell.getTreeItem();
						System.out.println(treeItem.toString());
					}
				}
			});
			
			
			
			return cell ;
		});
	}

	public String getText() {
		return null;
	}
}
