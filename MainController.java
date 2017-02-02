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
	public ComboBox<Recipie> combobox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Operations operations = new Operations();
		operations.showRecipies();
		combobox.setItems(operations.user.recipies);
	}

}
