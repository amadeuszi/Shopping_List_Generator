package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class MainController implements Initializable {
	
	Operations operations = new Operations();
	
	@FXML
	public ComboBox<Recipie> combobox;
	
	@FXML
	public Button btnDescription;
	@FXML
	public Button btnAdd;
	@FXML
	public Button btnGenerate;
	
	@FXML
	public Button btnReset;
	
	@FXML
	public TextArea txt;
	
	@FXML
	public ListView<Recipie> list;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		operations.prepareRecipies();
		combobox.setItems(operations.user.recipies);
		list.setItems(operations.user.recipiesAdded);
	}
	
	public void descriptionAction(Event event) {
		Recipie recipie = combobox.getValue();
		txt.setText(recipie.description);
	}
	
	public void addAction(Event event) {
		Recipie recipie = combobox.getValue();
		operations.user.addRecipie(recipie.id);
		operations.user.recipiesAdded.add(recipie);
	}
	
	public void generateAction(Event event) {
		operations.updateMyShelf();
		operations.createShoppingList();
		operations.convertFromShoppingListToProductList();
		String productList = operations.user.showProductList();
		txt.setText(productList);
	}
	
	public void resetAction(Event event) {
		operations.user.recipiesAdded.clear();
		operations.user.chosenRecipies.clear();
	}

}
