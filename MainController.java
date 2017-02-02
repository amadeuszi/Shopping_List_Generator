package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class MainController implements Initializable {
	
	@FXML
	public ComboBox<String> combobox;
	
	ObservableList<String> list = FXCollections.observableArrayList("Mark", "Amadeusz");
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		combobox.setItems(list);
	}

}
