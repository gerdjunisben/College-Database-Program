package Util;

import java.util.LinkedList;

import Model.College;

public class Demo {
	public static void main(String[] args)
	{
		Connect.setQuery("https://api.data.gov/ed/collegescorecard/v1/schools.json?school.degrees_awarded.predominant=2,3,4&_fields=id,school.name,school.city,school.zip,latest.academics.program.bachelors.computer,latest.admissions.admission_rate.overall,latest.completion.completion_rate_4yr_150nt,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state,latest.student.size,latest.school.ownership&api_key=nSTCffEr2yiN7QOxzqiGTZ4W0SGs7YJTXz9WitvF&per_page=100");
		Connect.setConnection("colleges.sqlite");
		Connect.fillDataBase();
		
		/*
		
		String username = "gerdjunisben";
		String password = "Password1";
		
		//Connect.addUser(username, password);
		
		CurrentUser cu = CurrentUser.makeUser(Connect.getUser(username,password));
		System.out.println(cu);
		cu.addCollege(100937);
		cu.save();
		*/
		
		/*
		LinkedList<College> list = Connect.getCollegesAll();
		list.forEach((c)->{
			System.out.println(c);
		});
		*/
		
		Connect.closeConnection();
	}
}
