package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.ResultSet;

public class Operations {
	private String host = "jdbc:mysql://sql11.freemysqlhosting.net/sql11155281";
	private String username = "sql11155281";
	private String p = "TMQYFgLtBp";
	private Connection con = null;
	public User user = null;
	
	public Operations() {
		user = new User();
		try {
			con = DriverManager.getConnection(host , username, p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void showRecipies() {
		try {
			String sql = "SELECT * from RECIPIE";
			ResultSet recipies = null;
			Statement stmt1 = con.createStatement();
			recipies = stmt1.executeQuery(sql);
		
			while (recipies.next()) {
				System.out.println("---------------------------");
				int id = recipies.getInt(1);
				String title = recipies.getString(2);
				String description = recipies.getString(3);
				System.out.println(id + ". " + title);
				System.out.println(description);
				System.out.println("Ingredients: ");
				sql = "SELECT * FROM RECIPIE_INGREDIENT JOIN INGREDIENT ON id_ingredient = id WHERE id_recipie = " + id;
				//" WHERE id_recipie = " + id;
				Statement stmt2 = con.createStatement();
				ResultSet ingredients = stmt2.executeQuery(sql);
				while(ingredients.next()) {
					double quantity = ingredients.getDouble("quantity");
					String ingredient = ingredients.getString("title");
					String kind = ingredients.getString("kind_of_quantity");
					System.out.println(ingredient + " - " + quantity + " " + kind);
					
				}
				System.out.println("---------------------------");
			} 
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addRecipies() {
		System.out.println("Which recipies to add? Press 0 to end");
		Scanner sc = new Scanner(System.in);
		int recipie = sc.nextInt();
		while (recipie != 0) {
			user.addRecipie(recipie);
			recipie = sc.nextInt();
		}
		sc.close();
	}
	
	void createShoppingList() {
		user.neededIngredients = new HashMap<Integer, Double>();
		try {
			ArrayList<Integer> recipies = user.getChosenRecipies();
			Statement stmt = con.createStatement();
			for (Integer recipie : recipies) {
				String sql = "SELECT * FROM RECIPIE_INGREDIENT WHERE id_recipie = " + recipie;
				ResultSet ingredients = stmt.executeQuery(sql);
				while (ingredients.next()) {
					int ingredient = ingredients.getInt("id_ingredient");
					double quantity = ingredients.getDouble("quantity");
					double howMuch = user.neededIngredients.getOrDefault(ingredient, 0.0);
					user.neededIngredients.put(ingredient, howMuch + quantity);
				}
			}
			user.convertToShoppingList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void convertFromShoppingListToProductList() {
		try {
			user.productList = new ArrayList<Product>();
			for (Integer key : user.shoppingList.keySet()) {
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM INGREDIENT JOIN PRICES ON id = id_ingredient AND id = " + key;
				ResultSet ingredient = stmt.executeQuery(sql);
				Double howMuch = user.shoppingList.get(key);
				if (ingredient.next()) {
					String name = ingredient.getString("title");
					Double onePack = ingredient.getDouble("quantity");
					Double numberOfPieces = Math.ceil(howMuch / onePack);
					Double price = ingredient.getDouble("price");
					String kind = ingredient.getString("kind_of_quantity");
					user.productList.add(new Product(name, onePack * numberOfPieces, numberOfPieces, 
							price, price * numberOfPieces, kind));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void updateMyShelf() {
		try {
			
			user.myShelf = new HashMap<Integer, Double>();
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM MY_SHELF";
			ResultSet shelf = stmt.executeQuery(sql);
			while (shelf.next()) {
				int ingredient = shelf.getInt("id_ingredient");
				double quantity = shelf.getDouble("quantity");
				user.myShelf.put(ingredient, quantity);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	int autoIncrementValue(String table) {
		try {
			Statement stmt = con.createStatement();
			String sql = "SHOW TABLE STATUS FROM " + 
						username + " WHERE `name` LIKE '" + 
						table + "'" ;
			ResultSet auto =  stmt.executeQuery(sql);
			if (auto.next()) {
				return auto.getInt("AUTO_INCREMENT");
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	void addRecipie(String title, String description, ArrayList<String> ingredients, ArrayList<Double> quantities) {
		try {
			ArrayList<Integer> ingr = translate(ingredients);
			int recipie = autoIncrementValue("RECIPIE");
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO RECIPIE(title, description) VALUES('" + 
						title + "', '" + description + "')";
			stmt.executeUpdate(sql);
			for (int i = 0; i < ingr.size(); i++) {
				addRecipieIngredient(recipie, ingr.get(i), quantities.get(i));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addRecipieIngredient(int recipie, int ingredient, double quantity) {
		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO RECIPIE_INGREDIENT VALUES(" + 
			recipie + ", " + ingredient + ", " + quantity + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addIngredient(String title, String kind, double price, double quantity) {
		try {
			
			Statement stmt = con.createStatement();
			int ingredient = autoIncrementValue("INGREDIENT");
			String sql = "INSERT INTO INGREDIENT(title, kind_of_quantity) VALUES('"
					+ title + "', '" + kind + "')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO PRICES VALUES(" + ingredient + ", " + price + ", " + quantity + ")"; 
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void updateTranslator() {
		try {
			
			user.translatorIngredient = new HashMap<String, Integer>();
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM INGREDIENT";
			ResultSet res = stmt.executeQuery(sql);
			while (res.next()) {
				String name = res.getString("title");
				int id = res.getInt("id");
				user.translatorIngredient.put(name, id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<Integer> translate(ArrayList<String> input) {
		ArrayList<Integer> result = new ArrayList<>();
		for (String in : input) {
			result.add(user.translatorIngredient.get(in));
		}
		return result;
	}
	
	void addToMyShelf(String name, double quantity) {
		int id = user.translatorIngredient.get(name);
		try {
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	                   ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * FROM MY_SHELF WHERE id_ingredient = " + id;
			ResultSet result = stmt.executeQuery(sql);
			double howMuch = 0;
			if (result.next()) {
				howMuch = result.getDouble("quantity");
				result.updateFloat("quantity", (float) (howMuch + quantity));
				result.updateRow();
			}
			else {
				Statement stmt2 = con.createStatement();
				sql = "INSERT INTO MY_SHELF VALUES(" + id + ", " + quantity + ")";
				stmt2.executeUpdate(sql);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	void deleteFromMyShelf(String name, double quantity) {
		int id = user.translatorIngredient.get(name);
		try {
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	                   ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * FROM MY_SHELF WHERE id_ingredient = " + id;
			ResultSet result = stmt.executeQuery(sql);
			double howMuch = 0;
			if (result.next()) {
				howMuch = result.getDouble("quantity");
				result.updateFloat("quantity", (float) Math.max(howMuch - quantity, 0.0));
				result.updateRow();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

