package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Init {
	public static void init() {
		try {
			String host = "jdbc:mysql://sql11.freemysqlhosting.net/sql11155281";
			String username = "sql11155281";
			String password = "TMQYFgLtBp";
			Connection con = null;
			con = DriverManager.getConnection(host , username, password);
			Statement stmt = null;
			String sql;
			stmt = con.createStatement();
			
			sql = "DROP TABLE RECIPIE_INGREDIENT";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE PRICES";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE MY_SHELF";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE INGREDIENT";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE RECIPIE";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE RECIPIE ("
					+ "id INTEGER not NULL AUTO_INCREMENT,"
					+ "title VARCHAR(100) not NULL,"
					+ "description VARCHAR(500) not NULL,"
					+ "PRIMARY KEY (id)"
					+ ")"; 
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE INGREDIENT ("
					+ "id INTEGER not NULL AUTO_INCREMENT,"
					+ "title VARCHAR(100) not NULL,"
					+ "kind_of_quantity VARCHAR(10) not NULL,"
					+ "PRIMARY KEY (id)"
					+ ")"; 
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE RECIPIE_INGREDIENT ("
					+ "id_recipie INTEGER not NULL,"
					+ "id_ingredient INTEGER not NULL,"
					+ "quantity FLOAT not NULL,"
					+ "PRIMARY KEY (id_recipie, id_ingredient),"
					+ "FOREIGN KEY (id_recipie) REFERENCES RECIPIE(id),"
					+ "FOREIGN KEY (id_ingredient) REFERENCES INGREDIENT(id)"
					+ ")";
	
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE PRICES ("
					+ "id_ingredient INTEGER not NULL,"
					+ "price FLOAT not NULL,"
					+ "quantity FLOAT not NULL,"
					+ "PRIMARY KEY (id_ingredient),"
					+ "FOREIGN KEY (id_ingredient) REFERENCES INGREDIENT(id)"
					+ ")"; 
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE MY_SHELF ("
					+ "id_ingredient INTEGER not NULL,"
					+ "quantity FLOAT not NULL,"
					+ "PRIMARY KEY (id_ingredient),"
					+ "FOREIGN KEY (id_ingredient) REFERENCES INGREDIENT(id)"
					+ ")"; 
			stmt.executeUpdate(sql);
			sql = "INSERT INTO INGREDIENT(title, kind_of_quantity) VALUES('egg', 'pc.')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO INGREDIENT(title, kind_of_quantity) VALUES('salt', 'pinch')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO INGREDIENT(title, kind_of_quantity) VALUES('cheese', 'gram')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO INGREDIENT(title, kind_of_quantity) VALUES('apple', 'pc.')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO RECIPIE(title, description) VALUES('Fried eggs with cheese', 'Put eggs on hot pan, add salt and grate cheeese, wait 5 minutes')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO RECIPIE(title, description) VALUES('Boiled eggs', 'Put two eggs in boiling water, wait 10 mins')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO RECIPIE_INGREDIENT VALUES(1, 1, 3.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO RECIPIE_INGREDIENT VALUES(1, 2, 2.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO RECIPIE_INGREDIENT VALUES(1, 3, 80.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO RECIPIE_INGREDIENT VALUES(2, 1, 2.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO PRICES VALUES(1, 3.99, 10.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO PRICES VALUES(2, 0.89, 1000.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO PRICES VALUES(3, 6.29, 250.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO PRICES VALUES(4, 0.50, 1.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO MY_SHELF VALUES(1, 1.0)";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO MY_SHELF VALUES(3, 100.0) ";
			stmt.executeUpdate(sql);
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
