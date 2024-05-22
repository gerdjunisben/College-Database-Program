package Model;
/**
 * The base class of all Query
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Model/Query.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 17th, 2022
 *
 */
public abstract class Query {
	private String attribute;
	private String query;
	
	public abstract String getQuery();
	public abstract String getAttribute();
}
