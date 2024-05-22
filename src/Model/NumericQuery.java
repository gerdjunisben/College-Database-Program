package Model;
/**
 * The decorator class for Query to let it take numerical values
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Model/NumericQuery.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 17th, 2022
 *
 */
public class NumericQuery extends QueryDecorator {
	private Query query;
	private String symbol;
	
	/**
	 * constuctor that decorates a query and takes a symbol to decide how the number will be compared
	 * @param query
	 * @param symbol
	 */
	public NumericQuery(Query query,String symbol)
	{
		this.query = query;
		this.symbol = symbol;
	}
	
	/**
	 * returns the object as a string
	 * @return String
	 */
	public String toString()
	{
		return query.toString() + " "+ symbol + " " + Double.parseDouble(query.getQuery());
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
