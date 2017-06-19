package com.seanrmilligan.sitebuilder.view;

import com.seanrmilligan.sitebuilder.model.SiteBuilderView;
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
    GridPane body;
    MenuBar menus;
    Menu fileMenu;
	MenuItem newSiteMenuItem;
    MenuItem openSiteMenuItem;
    MenuItem closeSiteMenuItem;
    MenuItem quitProgMenuItem;

    Label siteNameLabel;
    Label siteName;
    Label siteDomainLabel;
    Label siteDomain;

    public SiteBuilderWindow (Stage primaryStage){
        this.primaryStage = primaryStage;
        this.pane = new BorderPane();
        this.body = new GridPane();

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

        this.menus.getMenus().add(this.fileMenu);

        this.pane.setTop(this.menus);

        this.siteNameLabel = new Label("Site Name: ");
        this.siteName = new Label("");
        this.siteDomainLabel = new Label ("Site Domain: ");
        this.siteDomain = new Label("");

        this.body.add(this.siteNameLabel, 0, 0);
        this.body.add(this.siteName, 1, 0);
        this.body.add(this.siteDomainLabel, 2, 0);
        this.body.add(this.siteDomain, 3, 0);
		this.body.setPadding(new Insets(10));
		this.body.setHgap(5);
		this.body.setVgap(5);

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
