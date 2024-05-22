package Model;

/**
 * A basic model for query
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Model/BasicQuery.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 17th, 2022
 *
 */
public class BasicQuery extends Query {
	private String attribute;
	private String query;
	
	/**
	 * Constructor setting all vars
	 * @param attribute
	 * @param query
	 * @param type
	 */
	public BasicQuery(String attribute, String query) {
		super();
		this.attribute = attribute;
		this.query = query;
	}
	
	/**
	 * gets attribute
	 * @return String
	 */
	public String getAttribute() {
		return attribute;
	}
	
	/**
	 * sets attribute
	 * @param String attribute
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * gets query
	 * @return String
	 */
	public String getQuery() {
		return query;
	}
	
	/**
	 * sets query
	 * @param query
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	/**
	 * returns the object as a String
	 * @return String
	 */
	public String toString()
	{
		return attribute;
	}
}
