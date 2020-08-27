package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDAO {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	/*public List<Food> listaVertici(int porzioni){
		String sql = "SELECT f.food_code, f.display_name " + 
				"FROM  `portion` AS p, food AS f " + 
				"WHERE f.food_code= p.food_code " + 
				"GROUP BY f.food_code, f.display_name " + 
				"HAVING COUNT(p.portion_id)<= ? "
				+ "ORDER BY f.display_name" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			st.setInt(1, porzioni);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	*/
	public List<Food> listFoodWithPortions(int porzioni){
		String sql = "SELECT f.food_code, f.display_name " + 
				"FROM food AS f, `portion` AS o " + 
				"WHERE f.food_code= o.food_code " + 
				"GROUP BY f.food_code " + 
				"HAVING COUNT(o.portion_id) <= ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			st.setInt(1, porzioni);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"), res.getString("display_name")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Arco> listArchi(int porzioni){
		String sql = "SELECT f1.food_code AS id1, f1.display_name AS n1, f2.food_code AS id2, f2.display_name AS n2, AVG(c1.condiment_calories) AS peso " + 
				"FROM food AS f1, `portion` AS o1, food AS f2, `portion` AS o2, " + 
				"     food_condiment AS fd1, food_condiment AS fd2, condiment AS c1, condiment AS c2 " + 
				"WHERE f1.food_code= o1.food_code AND f2.food_code= o2.food_code " + 
				"      AND fd1.food_code= f1.food_code AND fd2.food_code= f2.food_code " + 
				"		AND fd1.condiment_code= c1.condiment_code AND fd2.condiment_code= c2.condiment_code " + 
				"		AND c1.condiment_code= c2.condiment_code AND f1.food_code < f2.food_code " + 
				"GROUP BY f1.food_code, f2.food_code " + 
				"HAVING COUNT(o1.portion_id) <= ? AND COUNT(o2.portion_id) <= ? "
				+ "ORDER BY f1.display_name " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Arco> list = new ArrayList<>() ;
			st.setInt(1, porzioni);
			st.setInt(2, porzioni);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Arco(new Food(res.getInt("id1"), res.getString("n1")),
							new Food(res.getInt("id1"), res.getString("n1")), res.getDouble("peso")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
}
