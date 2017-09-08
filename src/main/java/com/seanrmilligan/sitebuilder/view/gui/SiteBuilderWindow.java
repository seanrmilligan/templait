package com.seanrmilligan.sitebuilder.view.gui;

import com.seanrmilligan.sitebuilder.controller.FileController;
import com.seanrmilligan.sitebuilder.file.FileManager;
import com.seanrmilligan.sitebuilder.view.MultiFileView;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.IOException;

public class SiteBuilderWindow implements MultiFileView {
	private FileController fileController;
	private FileManager files;
	
	private Stage primaryStage;
	private Scene scene;
	private MenuBar menus;
	private Menu fileMenu;
	private MenuItem newSiteMenuItem;
	private MenuItem openSiteMenuItem;
	private MenuItem closeSiteMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem quitProgMenuItem;
	private Menu editMenu;
	private MenuItem editSiteMenuItem;
	private MenuItem editMapMenuItem;
	private Menu buildMenu;
	private MenuItem buildCurrentTabMenuItem;
	private MenuItem buildAllTabsMenuItem;
	private MenuItem buildAllMenuItem;
	private BorderPane body;
	private TreeView<File> directoryTree;
	private String pathPrefix;
	private String pathReplacement;
	private TabPane editorTabs;
	private ToolBar toolbar;
	
	
	
	public SiteBuilderWindow (Stage primaryStage){
		this.primaryStage = primaryStage;
		this.body = new BorderPane();

		this.initMenus();
		this.initFileViewer();
		this.initTabs();
		this.initToolbar();
		this.initEvents();
		
		this.body.setTop(this.menus);
		this.body.setLeft(this.directoryTree);
		this.body.setCenter(this.editorTabs);
		this.body.setBottom(this.toolbar);

		this.scene = new Scene(this.body);
		this.primaryStage.setScene(this.scene);
	}
	
	public void setFileController(FileController fileController) {
		this.fileController = fileController;
	}
	
	public void setFileManager(FileManager fileManager) {
		this.files = fileManager;
	}
	
	public void setDirectoryTree(TreeItem<File> tree) { this.directoryTree.setRoot(tree); }
	
	public void setDirectoryTreePathTruncation(String pathPrefix, String replacement) {
		this.pathPrefix = pathPrefix;
		this.pathReplacement = replacement;
		this.updateCellFactory();
	}
	
	public void setTitleBar (String title) { this.primaryStage.setTitle(title); }
	
	public void show() { this.primaryStage.show(); }
	
	public void setMaximized (boolean maximized) { primaryStage.setMaximized(maximized); }
	
	private void initMenus() {
		this.menus = new MenuBar();
		this.fileMenu = new Menu("_File");
		this.fileMenu.setMnemonicParsing(true);
		
		this.newSiteMenuItem = new MenuItem("_New Site");
		this.newSiteMenuItem.setMnemonicParsing(true);
		this.newSiteMenuItem.setOnAction(event -> {
		
		});
		this.openSiteMenuItem = new MenuItem("_Open Site");
		this.openSiteMenuItem.setMnemonicParsing(true);
		this.closeSiteMenuItem = new MenuItem("_Close Site");
		this.closeSiteMenuItem.setMnemonicParsing(true);
		this.saveFileMenuItem = new MenuItem("_Save");
		this.quitProgMenuItem = new MenuItem("_Quit");
		this.quitProgMenuItem.setMnemonicParsing(true);
		
		this.fileMenu.getItems().addAll(
			this.newSiteMenuItem,
			this.openSiteMenuItem,
			this.closeSiteMenuItem,
			this.saveFileMenuItem,
			this.quitProgMenuItem
		);
		
		this.editMenu = new Menu("_Edit");
		this.editMenu.setMnemonicParsing(true);
		
		this.editSiteMenuItem = new MenuItem("S_ite");
		this.editSiteMenuItem.setMnemonicParsing(true);
		this.editMapMenuItem = new MenuItem("_Key Map");
		this.editMapMenuItem.setMnemonicParsing(true);
		
		this.editMenu.getItems().addAll(
			this.editSiteMenuItem,
			this.editMapMenuItem
		);
		
		this.buildMenu = new Menu("_Build");
		this.buildCurrentTabMenuItem = new MenuItem("Current Tab");
		this.buildAllTabsMenuItem = new MenuItem("All Tabs");
		this.buildAllMenuItem = new MenuItem("All Files");
		
		this.buildMenu.getItems().addAll(
			this.buildCurrentTabMenuItem,
			this.buildAllTabsMenuItem,
			this.buildAllMenuItem
		);
		
		
		this.menus.getMenus().addAll(
			this.fileMenu,
			this.editMenu,
			this.buildMenu
		);
	}
	
	private void initFileViewer() {
		this.directoryTree = new TreeView<>();
		this.pathPrefix = "";
		this.updateCellFactory();
	}
	
	private void initTabs() {
		this.editorTabs = new TabPane();
	}
	
	private void initToolbar() {
		this.toolbar = new ToolBar();
	}
	
	private void initEvents() {
		this.buildAllMenuItem.setOnAction(e -> {
			try {
				this.fileController.buildAll();
			} catch (IOException ex) {
				System.err.println(ex.toString());
			}
		});
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
						this.files.open(this, cell.getTreeItem().getValue());
					}
				}
			});
			
			return cell ;
		});
	}

	public void addFile(String title, String text) {
		Tab newTab = new Tab();
		CodeArea editor = new CodeArea();
		editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));
		editor.replaceText(0,0,text);
		editor.textProperty().addListener((observable, oldVal, newVal) -> {
			System.out.println("Changed");
		});
		editor.setOnKeyPressed(event -> {
			KeyCodeCombination save = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
			if (save.match(event)) {
				System.out.println("Save");
			}
		});
		newTab.setText(title);
		newTab.setContent(editor);
		this.editorTabs.getTabs().add(newTab);
	}
}
