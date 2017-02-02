package application;

public class Recipie {
	public int id;
	public String title;
	public String description;
	public Recipie(int id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
