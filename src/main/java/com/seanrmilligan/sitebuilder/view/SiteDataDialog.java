package com.seanrmilligan.sitebuilder.view;

import com.seanrmilligan.javafx.scene.control.IntegerField;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

import static com.seanrmilligan.sitebuilder.view.Constants.MIN_HEIGHT;
import static com.seanrmilligan.sitebuilder.view.Constants.MIN_WIDTH;

/**
 * Created by sean on 6/5/17.
 */
public class SiteDataDialog extends Stage {
    Scene scene;
    private GridPane pane;
    private Label siteNameLabel;
    private TextField siteName;
    private ComboBox<String> schema;
    private TextField siteDomain;
    private IntegerField port;
    private Button submit;

    public SiteDataDialog(Stage owner) {
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);

        this.pane = new GridPane();
        this.siteNameLabel = new Label("Project Name: ");
        this.siteName = new TextField();

        this.schema = new ComboBox<>();
        this.schema.setPrefWidth(100);
        this.siteDomain = new TextField();
        this.siteDomain.setPrefWidth(300);
        this.port = new IntegerField();
        this.port.setPrefWidth(100);

        this.submit = new Button();

        this.submit.setOnAction(e -> {
            SiteDataDialog.this.hide();
        });

        this.pane.add(this.siteNameLabel, 0, 0, 1, 1);
        this.pane.add(this.siteName, 1, 0, 2, 1);
        this.pane.add(this.schema, 0, 1, 1, 1);
        this.pane.add(this.siteDomain, 1, 1, 1, 1);
        this.pane.add(this.port, 2, 1, 1, 1);
        this.pane.add(this.submit, 1, 2, 1,1 );
        this.pane.setPadding(new Insets(10));
        this.pane.setHgap(5);
        this.pane.setVgap(5);

        this.pane.setMinWidth(MIN_WIDTH);
        this.pane.setMinHeight(MIN_HEIGHT);
        this.scene = new Scene(this.pane);
        this.setScene(this.scene);
    }

    public void init(String title, String submitText) {
        this.setTitle(title);
        this.siteName.setText("");
        this.siteDomain.setText("");
        this.submit.setText(submitText);
    }

    public String getSiteName() {
        return this.siteName.getText();
    }
    public void setSiteName(String name) { this.siteName.setText(name); }
    public String getSchema() { return this.schema.getSelectionModel().getSelectedItem(); }
    public void setSchema(String schema) { this.schema.getSelectionModel().select(schema); }
    public String getSiteDomain() { return this.siteDomain.getText(); }
    public void setSiteDomain(String domain) { this.siteDomain.setText(domain); }
    public void setPort(int port) { this.port.setText(Integer.toString(port)); }
    public int getPort() { return Integer.parseInt(this.port.getText()); }

	public void setSchemaList(List<String> schema) {
		this.schema.getItems().clear();
		this.schema.getItems().addAll(schema);
	}
}
