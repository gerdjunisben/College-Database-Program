package Model;

/**
 * The data model for colleges
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Model/College.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */
public class College {
    private String id;
    private String name;
    private String city;
    private String zip;
    private double admissionRate;
    private double completionRate;
    private int inStateTuition;
    private int outStateTuition;
    private int population;
    private String ownership;
    
    /**
     * Constructor setting all vars
     * @param id
     * @param name
     * @param city
     * @param zip
     * @param admissionRate
     * @param completionRate
     * @param inStateTuition
     * @param outStateTuition
     * @param population
     * @param ownership
     */
    public College(String id,String name,String city,String zip,
    double admissionRate,double completionRate,int inStateTuition,int outStateTuition
    ,int population, String ownership)
    {
        this.id=id;
        this.name=name;
        this.city=city;
        this.zip=zip;
        this.admissionRate = admissionRate;
        this.completionRate = completionRate;
        this.inStateTuition = inStateTuition;
        this.outStateTuition = outStateTuition;
        this.population = population;
        this.ownership = ownership;
    }

    /**
     * gets id
     * @return String
     */
    public String getId()
    {
        return id;
    }

    /**
     * gets name
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * gets city
     * @return String
     */
    public String getCity()
    {
        return city;
    }

    /**
     * gets zip
     * @return String
     */
    public String getZip()
    {
        return zip;
    }

    /**
     * gets admissionRate formatted
     * @return String
     */
    public String getAdmissionRate()
    {
    	return String.format("%2.2f",admissionRate) + "%";
    }

    /**
     * sets admissionRate
     * @param double admissionRate
     */
    public void setAdmissionRate(double admissionRate)
    {
        this.admissionRate = admissionRate;
    }

    /**
     * gets completionRate formatted
     * @return String
     */
    public String getCompletionRate()
    {
        return String.format("%2.2f",completionRate)+ "%";
    }

    /**
     * sets completionRate
     * @param double completionRate
     */
    public void setCompletionRate(double completionRate)
    {
        this.completionRate = completionRate;
    }

    /**
     * gets inStateTuition formatted
     * @return String
     */
    public String getInStateTuition()
    {
        return "$" + String.format("%,d",inStateTuition);
    }

    /**
     * sets inStateTuition
     * @param int inStateTuition
     */
    public void setInStateTuition(int inStateTuition)
    {
        this.inStateTuition = inStateTuition;
    }

    /**
     * gets outStateTuition formatted
     * @return String
     */
    public String getOutStateTuition()
    {
        return "$" + String.format("%,d",outStateTuition);
    }

    /**
     * sets outStateTuition
     * @param int outStateTuition
     */
    public void setOutStateTuition(int outStateTuition)
    {
        this.outStateTuition = outStateTuition;
    }
    
    
    
    /**
     * gets ownership
     * @return String
     */
    public String getOwnership() {
		return ownership;
	}

    /**
     * sets ownership
     * @param String ownership
     */
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	 /**
     * gets population formatted
     * @return String
     */
	public String getPopulation() {
		return String.format("%,d",population);
	}

	 /**
     * sets population
     * @param int population
     */
	public void setPopulation(int population) {
		this.population = population;
	}

	 /**
     * returns college as a String
     * @return String
     */
	public String toString()
    {
        return String.format("Id:%-10s,Name:%-50s,City:%-20s,Zip:%-15s,Admission:%-15s,Completion:%-15s,InState Tuition:%-20s,OutState Tuition:%-20s,Population:%-10s,Ownership:%-10s",id,name,city,zip,getAdmissionRate(),getCompletionRate(),getInStateTuition(),getOutStateTuition(),getPopulation(),getOwnership());
    }
}
