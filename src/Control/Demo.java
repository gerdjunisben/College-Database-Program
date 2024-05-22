package Control;

import Util.Connect;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connect.setQuery("https://api.data.gov/ed/collegescorecard/v1/schools.json?school.degrees_awarded.predominant=2,3,4&_fields=id,school.name,school.city,school.zip,latest.academics.program.bachelors.computer,latest.admissions.admission_rate.overall,latest.completion.completion_rate_4yr_150nt,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state,latest.student.size,latest.school.ownership&api_key=nSTCffEr2yiN7QOxzqiGTZ4W0SGs7YJTXz9WitvF&per_page=100");
		Connect.setConnection("colleges.sqlite");
		Connect.fillDataBase();
	}

}
