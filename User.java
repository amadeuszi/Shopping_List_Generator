package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class User {
	private ArrayList<Integer> chosenRecipies;
	public Map<Integer, Double> shoppingList;
	public Map<Integer, Double> neededIngredients;
	public Map<Integer, Double> myShelf;
	public ArrayList<Product> productList;
	public Map<String, Integer> translatorIngredient;
	public ObservableList<Recipie> recipies;
	
	User() {
		chosenRecipies = new ArrayList<Integer>();
		shoppingList = new HashMap<Integer, Double>();
		neededIngredients = new HashMap<Integer, Double>();
		myShelf = new HashMap<Integer, Double>();
		productList = new ArrayList<>();
		recipies = FXCollections.observableArrayList();
	}
	void addRecipie(int recipie) {
		chosenRecipies.add(recipie);
	}
	ArrayList<Integer> getChosenRecipies() {
		return chosenRecipies;
	}
	
	void convertToShoppingList() {
		shoppingList = new HashMap<Integer, Double>();
		for (Integer key : neededIngredients.keySet()) {
			Double howMuch = myShelf.getOrDefault(key, 0.0);
			Double needed = neededIngredients.get(key);
			if (howMuch < needed) {
				shoppingList.put(key, needed - howMuch);
			}
		}
	}
	
	void showShoppingList() {
		System.out.println("\nIngredients to buy: ");
		for (Integer key : shoppingList.keySet()) {
			System.out.println("What: " + key + ", How much: "  + shoppingList.get(key));
		}
	}
	
	void showProductList() {
		System.out.println("\nProduct List: ");
		double sum = 0;
		for (Product product : productList) {
			System.out.println(product);
			sum += product.priceAll;
		}
		System.out.println("\n---------------------------\n" +
					"Price for all the products: " + sum);
	}
}
