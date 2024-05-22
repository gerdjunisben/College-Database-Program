package Model;

/**
 * The decorator class for Query to let it take Strings
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Model/StringQuery.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 17th, 2022
 *
 */
public class StringQuery extends QueryDecorator {
	private Query query;
	/**
	 * Constructor that decorates a query
	 * @param query
	 */
	public StringQuery(Query query)
	{
		this.query = query;
	}
	
	/**
	 * returns this object as a String
	 * @return String
	 */
	public String toString()
	{
		return query.toString() + " = '" + query.getQuery() + "'";
	}

	/**
	 * returns query
	 * @return String
	 */
	@Override
	public String getQuery() {
		return query.getQuery();
	}

	/**
	 * returns attribute
	 * @return String
	 */
	@Override
	public String getAttribute() {
		// TODO Auto-generated method stub
		return query.getAttribute();
	}
}
