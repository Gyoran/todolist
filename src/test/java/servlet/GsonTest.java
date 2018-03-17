package servlet;

import com.google.gson.Gson;

import main.actionPoint.*;

import org.junit.Test;
import java.time.LocalDate;

public class GsonTest {
	
	@Test
	public void gsonShouldOutputJsonString() {
		Gson gson = new Gson();
		String jsonString = gson.toJson(new ActionPoint("name", "descr", LocalDate.now()));
		System.out.println(jsonString);
		//assert jsonString == JSON Format
	}
	
	@Test
	public void actionPointToStringShouldOutputJsonString() {
		System.out.println(new ActionPoint("name", "descr", LocalDate.now()));
		//assert jsonString == JSON Format
	}
}
