package com.seanrmilligan.sitebuilder.view;

import com.seanrmilligan.sitebuilder.model.SiteBuilderView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.BreadCrumbBar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
	BreadCrumbBar breadcrumb;
	HBox infoBar;
    Label siteNameLabel;
    Label siteName;
    Label siteDomainLabel;
    Label siteDomain;

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

    public String getText() {
        return null;
    }
}
