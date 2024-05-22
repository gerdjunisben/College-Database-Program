package Model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import Util.Connect;

/**
 * The data model for users
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Model/User.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */
public class User {
    private String username;
    private int id;
    private TreeSet<Integer> list;
    private int elems;

    
/**
 * Constructor that sets all vars
 * @param username
 * @param id
 */
    public User(String username,int id)
    {
        this.username = username;
        elems=0;
        this.id = id;
        list = new TreeSet<>();
    }

    /**
     * gets username
     * @return String
     */
    public String getUsername()
    {
        return username;
    }
    
    /**
     * gets id
     * @return int
     */
    public int getID()
    {
    	return id;
    }
    
    /**
     * adds a college to the list if there is less than 10 ints in the list and it isn't already in the list
     * @param collegeID
     * @return 1 if success, 0 if it already exist, -1 if the list is at it's max (10), -2 if it doesn't exist in the database
     */
    public int addCollege(int collegeID)
    {
    	boolean existsInList = list.contains(collegeID);
    	boolean existsInDB = Connect.doesCollegeExist(collegeID);
    	if(!existsInList && existsInDB && elems<10)
    	{
    		list.add(collegeID);
        	elems++;
        	return 1;
    	}
    	
    	if(existsInList)
    	{
    		return 0;
    	}
    	else if(!existsInDB)
    	{
    		return -2;
    	}
    	else
    	{
    		return -1;
    	}
    }
    
    /**
     * removes a college by id
     * @param id
     */
    public void removeCollege(int id)
    {
    	list.remove(id);
    	elems--;
    	
    }
    
    /**
     * gets a college id at an index
     * @param index
     * @return int id
     */
    public int getCollege(int index)
    {
    	if(elems>index)
    	{
    		Iterator<Integer> it = list.iterator();
    		for(int i =0;i<index;i++)
    		{
    			it.next();
    		}
    		return it.next();
    	}
    	else
    	{
    		return 0;
    	}
    	
    }
    
    
    

    

    public String toString()
    {
        return username;
    }
}
