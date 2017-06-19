package com.seanrmilligan.javafx.scene.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Created by sean on 6/19/17.
 */
public class IntegerField extends TextField {
	public IntegerField() {
		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					IntegerField.this.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}
}
