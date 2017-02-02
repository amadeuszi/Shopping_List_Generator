package application;


public class Product {
	String name;
	double quantity;
	double howManyPieces;
	double price;
	double priceAll;
	String kind;
	public Product(String name, double quantity, double howManyPieces, double price, double priceAll, String kind) {
		this.name = name;
		this.quantity = quantity;
		this.howManyPieces = howManyPieces;
		this.price = price;
		this.priceAll = priceAll;
		this.kind = kind;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		s.append("Product name: " + name + "\n");
		s.append("How many: " + quantity + " " + kind + "\n");
		s.append("price for one piece: " + price + "\n");
		s.append("how manyPieces: " + howManyPieces + "\n");
		s.append("price forAll: " + priceAll + "\n");
		return s.toString();
	}
}
