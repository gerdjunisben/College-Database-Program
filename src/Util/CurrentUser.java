package Util;

import Model.User;
/**
 * The CurrentUser singleton for the connect class
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Util/CurrentUser.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */
public class CurrentUser {
	
	private static CurrentUser currentUser;
	private User user;
	
	/**
	 * Private constructor that sets user
	 * @param user
	 */
	private CurrentUser(User user)
	{
		this.user = user;
	}
	
	/**
	 * Singleton constructor making it so there is only one of this class
	 * @param user
	 * @return
	 */
	public static CurrentUser makeUser(User user)
	{
		if(currentUser==null)
			return new CurrentUser(user);
		else
			return currentUser;
	}
	
	/**
	 * adds a college to this classes' {@link Model.User} 
	 * @param collegeID
	 * @return {@link Model.User#addCollege(int)}
	 */
	public int addCollege(int collegeID)
	{
		return user.addCollege(collegeID);
	}
	
	/**
	 * removes a college from this {@link Model.User}
	 * @see {@link Model.User#removeCollege()}
	 * @param collegeID
	 */
	public void removeCollege(int collegeID)
	{
		user.removeCollege(collegeID);
	}
	
	/**
	 * save's this classes' user
	 * @see {@link Util.Connect#saveUser()}
	 */
	public void save()
	{
		Connect.saveUser(user);
	}
	
	/**
	 * gets a college from this classes' user
	 * @param index
	 * @return {@link Model.User#getCollege(int)}
	 */
	public int getCollege(int index)
	{
		return user.getCollege(index);
	}
	
	/**
	 * returns this class as a String
	 */
	public String toString()
	{
		return user.toString();
	}
	
	
	
	
}
