package Util;

import Model.College;
import Model.Query;
import Model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Utilities for college search and user login
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Util/Connect.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */

public class Connect {
	public static URL url;
	public static Connection db;
	public static String cQuery;
	public static CurrentUser current;
	
	/**
	 * updates the colleges table in the sqlite database using the query from {@link Control.SignInController}'s
	 * initialization 
	 * this is used for development purposes and is unused in this program
	 */
	public static void fillDataBase() 
	{
		try
		{
			setURL(cQuery);
			Statement statement = db.createStatement();
			statement.setQueryTimeout(30);
			String inLine = "";
			
			
		
			
			statement.executeUpdate("DROP TABLE IF EXISTS colleges"); 
			statement.executeUpdate(
			"CREATE TABLE colleges (ID INT(20) PRIMARY KEY, Name varchar(255), "+
			"City varchar(255), Zip varchar(255),Admission DOUBLE(5,2),Completion DOUBLE(5,2),"+
			"InState INT(10),OutState INT(10),Population INT(15), Ownership varchar(10))");
			
			/*
			statement.executeUpdate("DROP TABLE IF EXISTS users"); 
			statement.executeUpdate(
			"CREATE TABLE users ("
			+ "username	VARCHAR(255),"
			+ "password VARCHAR(255),"
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT"
			+ ")");
			
			statement.executeUpdate("DROP TABLE IF EXISTS savedList"); 
			statement.executeUpdate(
			"CREATE TABLE savedList("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "save1 INT(20),"
			+ "save2 INT(20),"
			+ "save3 INT(20),"
			+ "save4 INT(20),"
			+ "save5 INT(20),"
			+ "save6 INT(20),"
			+ "save7 INT(20),"
			+ "save8 INT(20),"
			+ "save9 INT(20),"
			+ "save10 INT(20)"
			+ ")");
			*/
			
			
			Scanner scanner = new Scanner(url.openStream());
			while(scanner.hasNextLine())
			{
				inLine += scanner.nextLine();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			
			
			JsonNode node = objectMapper.readValue(inLine,JsonNode.class);
			JsonNode array = node.get("results");
			JsonNode totalNode = node.get("metadata").get("total");
			int total = Integer.parseInt(totalNode.asText());
			
			
			for(int y = 0;y<= (total/100);y++)
			{
				System.out.println(y + "/" + (total/100));
				
				for(int x =0;x<array.size();x++)
				{
					JsonNode jsonNameNode = array.get(x);
					JsonNode compsciNode = jsonNameNode.get("latest.academics.program.bachelors.computer");
					if(compsciNode != null && compsciNode.asInt()==1)
					{
						
						JsonNode idNode = jsonNameNode.get("id");
						JsonNode nameNode = jsonNameNode.get("school.name");
						JsonNode cityNode = jsonNameNode.get("school.city");
						String cityName = cityNode.asText().replace("'", "");
						JsonNode zipNode = jsonNameNode.get("school.zip");
						String collegeName = nameNode.asText().replace("'", "");
						JsonNode admissionNode = jsonNameNode.get("latest.admissions.admission_rate.overall");
						double admission = admissionNode.asDouble()*100;
						JsonNode completionNode = jsonNameNode.get("latest.completion.completion_rate_4yr_150nt");
						double completion = completionNode.asDouble()*100;
						JsonNode inStateNode = jsonNameNode.get("latest.cost.tuition.in_state");
						JsonNode outStateNode = jsonNameNode.get("latest.cost.tuition.out_of_state");
						JsonNode populationNode = jsonNameNode.get("latest.student.size");
						JsonNode ownershipNode = jsonNameNode.get("latest.school.ownership");
						String ownership;
						if(ownershipNode.asInt()==1)
							ownership="public";
						else
							ownership="private";
						statement.executeUpdate(
						"INSERT INTO colleges (ID,Name,City,Zip,Admission,Completion,InState,OutState,population,ownership) "+
						"VALUES ('"+idNode.asInt()+"','"+collegeName+"','"+cityName+"','"+zipNode.asText()+"','"+
						admission+"','"+completion+"','"+inStateNode.asInt()+"','"+
						outStateNode.asInt()+"','"+populationNode.asInt()+"','"+ ownership+"')");
					}
					
				}
				if(y==34)
					break;
				setURL(cQuery+ "&page=" +(y+1));
				statement.setQueryTimeout(30);
				inLine = "";
				scanner = new Scanner(url.openStream());
				while(scanner.hasNextLine())
				{
					inLine += scanner.nextLine();
				}
				objectMapper = new ObjectMapper();
				
				
				node = objectMapper.readValue(inLine,JsonNode.class);
				array = node.get("results");
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * saves {@link CurrentUser} using it's save method
	 */
	public static void saveCurrent()
	{
		if(current!=null)
			current.save();
	}
	
	/**
	 * saves a {@link Model.User} in the users table in the sqlite database
	 * @param user
	 */
	public static void saveUser(User user)
	{
		try {
			Statement statement = db.createStatement();
			statement.setQueryTimeout(30);
			String s = "UPDATE savedList SET ";
			int index =0;
			int college = user.getCollege(index);
			if(college!=0)
			{
				while(college!=0)
				{
					if(index>0)
						s+= ",";
					s += "save" + (index+1) + "='" + user.getCollege(index) + "'";
					
					index++;
					college = user.getCollege(index);
					
				}
				s += " WHERE id=" +user.getID();
				System.out.println(s);
				statement.executeUpdate(s);
			}
			else
			{
				System.out.println("nothing to save");
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Signs in using a username and password
	 * @param username
	 * @param password
	 * @return 0 if user doesn't exist, -1 if failed for unknown reason, else success
	 */
	public static int signIn(String username,String password)
	{
		
		try {
			Statement statement = db.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE username='" + username+"'");
			if(rs.next())
			{
				if(rs.getString("password").equals(password))
				{
					return rs.getInt("id");
				}
			}
			else
			{
				System.out.println("User doesn't exist");
				return 0;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * returns a User object or null if the id is -1 see {@link #signIn}
	 * @param username
	 * @param password
	 * @return User
	 */
	public static User getUser(String username,String password)
	{
		int id = signIn(username,password);
		User user;
		if(id!=-1)
		{
			user = new User(username,id);
			
			try {
				Statement statement = db.createStatement();
				statement.setQueryTimeout(30);
				ResultSet rs = statement.executeQuery("SELECT * FROM savedList WHERE id='" + id+"'");
				for(int i =1;i<=10;i++)
				{
					int save = rs.getInt("save" + i);
					if(save!=0)
						user.addCollege(save);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			user=null;
		}
		return user;
	}
	
	/**
	 * adds a user if the username doesn't already exist
	 * @param username
	 * @param password
	 * @return 1 if success, 0 if it already exists, -1 on unknown failure
	 */
	public static int addUser(String username,String password)
	{
		
		try {
			Statement statement = db.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE username='" + username+"'");
			
			if(!rs.next())
			{
				statement.executeUpdate(
						"INSERT INTO users (username,password) "+
						"VALUES ('"+username+"','"+password+"')");
				statement.executeUpdate(
						"INSERT INTO savedList (save1,save2,save3,save4,save5,save6,save7,save8,save9,save10) "+
						"VALUES ('"+0+"','"+0+"','"+0+"','"+0+
						"','"+0+"','"+0+"','"+0+"','"+0+
						"','"+0+"','"+0+"')");
				return 1;
			}
			else
			{
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * returns true if the id exists in the db and false otherwise
	 * @param id
	 * @return boolean
	 */
	public static boolean doesCollegeExist(int id)
	{
		
		try {
			Statement statement;
			statement = db.createStatement();
			statement.setQueryTimeout(30);
			String query = "SELECT * FROM colleges WHERE id=" + id;

			ResultSet rs = statement.executeQuery(query);
			
			if(rs.next())
				return true;
			else
				return false;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * returns a LinkedList of all Colleges obtained with the queries using the list of {@link Model.StringQuery}
	 * @param observableList
	 * @return LinkedList<College>
	 */
	public static LinkedList<College> getSomeColleges(ObservableList<Query> observableList)
	{
		Statement statement;
		LinkedList<College> list = new LinkedList<>();
		try {
			statement = db.createStatement();
			statement.setQueryTimeout(30);
			String query = "SELECT * FROM colleges WHERE (";
			for(int i =0;i<observableList.size();i++)
			{
				query += observableList.get(i).toString();
				if(i!=observableList.size()-1)
				{
					query+= " AND ";
				}
			}
			query += ")";
			System.out.println(query);

			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next())
			{

				list.add(new College(rs.getString("ID"),rs.getString("Name"),rs.getString("City"),rs.getString("Zip"),rs.getDouble("Admission"),rs.getDouble("Completion"),rs.getInt("InState"),rs.getInt("OutState"),rs.getInt("Population"),rs.getString("Ownership")));
				
				
			}
			return list;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * gets all colleges saved in the {@link CurrentUser}
	 * @return LinkedList<College>
	 */
	public static LinkedList<College> getSavedColleges()
	{
		
		Statement statement;
		LinkedList<College> list = new LinkedList<>();
		try {
			statement = db.createStatement();
			statement.setQueryTimeout(30);
			String query = "SELECT * FROM colleges WHERE (";
			int i = 0;
			int id=current.getCollege(i);
			
			if(id==0)
				return null;
			while(id!=0)
			{
				query += "id="+id;
				id = current.getCollege(++i);
				if(id!=0)
				{
					query+= " OR ";
				}
			}
			
			query += ")";
			System.out.println(query);

			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next())
			{

				list.add(new College(rs.getString("ID"),rs.getString("Name"),rs.getString("City"),rs.getString("Zip"),rs.getDouble("Admission"),rs.getDouble("Completion"),rs.getInt("InState"),rs.getInt("OutState"),rs.getInt("Population"),rs.getString("Ownership")));
				
				
			}
			return list;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * returns a LinkedList of all colleges, this is for testing purposes only
	 * @return LinkedList<College>
	 */
	public static LinkedList<College> getCollegesAll()
	{
		Statement statement;
		LinkedList<College> list = new LinkedList<>();
		try {
			statement = db.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT * FROM colleges");
			
			while(rs.next())
			{

				list.add(new College(rs.getString("ID"),rs.getString("Name"),rs.getString("City"),rs.getString("Zip"),rs.getDouble("Admission"),rs.getDouble("Completion"),rs.getInt("InState"),rs.getInt("OutState"),rs.getInt("Population"),rs.getString("Ownership")));
				
				
			}
			return list;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
		
	}
	
	/**
	 * sets the query String
	 * @param query
	 */
	public static void setQuery(String query)
	{
		cQuery = query;
	}
	
	/**
	 * sets URL for pulling data 
	 * @param query
	 */
	public static void setURL(String query)
	{
		
		try {
			URL urlT = new URL(query);
			HttpURLConnection conn = (HttpURLConnection)urlT.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();
			
			if(responseCode!=200)
			{
				throw new RuntimeException("Response failed");
			}
			else
			{
				url = urlT;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * sets the connection to the database
	 * @param databaseName
	 */
	public static void setConnection(String databaseName)
	{
		try {
			Class.forName("org.sqlite.JDBC");
			db= DriverManager.getConnection("jdbc:sqlite:" + databaseName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * closes the connection to the database
	 * @return true on success
	 */
	public static boolean closeConnection()
	{
		try {
			if(db != null)
			{
				db.close();
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
