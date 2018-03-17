package main.actionPoint;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ActionPointController {
	
	@Autowired 
	private ActionPointManager apManager;
	@Autowired
	private ActionPointDao actionPointDao;
	@Autowired
	private ActionPointByDateComparer apbdc;
	@Autowired 
	private ActionPointByStateComparer apbsc;
	
	@RequestMapping(value="/points", method=RequestMethod.GET)
	public String all( 
			@RequestParam(value = "fsort", required = false, defaultValue = "no") String fsort,
			@RequestParam(value = "dsort", required = false, defaultValue = "no") String dsort) {
		String result = "{\"points\":[";
		List<ActionPoint> actionPoints = actionPointDao.findAll();
		if (fsort.equals("yes"))
			Collections.sort(actionPoints, apbsc);
		if (dsort.equals("yes"))
			Collections.sort(actionPoints, apbdc);
		int i = 1;
		for (ActionPoint index: actionPoints) {
			if (i != 1)
				result += ",";
			result += index.toString();
			i++;
		}	
		return result + "]}";
	}
	
	@RequestMapping(value="/special_error", method=RequestMethod.GET) 
	public String error(
			@RequestParam(value = "e", required = false, defaultValue = "Unknown Error") String error) {
		return error;
	}
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST) 
	@ResponseBody 
	public ActionPoint updateActionPoint(@PathVariable("id") String strid, 
           @RequestParam(required = false) String name, 
           @RequestParam(required = false) String description,
           @RequestParam(value = "deadline", required = false, defaultValue = "unknown") String strdeadline,
           @RequestParam(value = "finished", required = false, defaultValue = "unknown") String strfinished) {
		try {
			long id = Long.parseLong(strid);
			Boolean finished = null;
			LocalDate deadline = null;
			if (!strfinished.equals("unknown"))
				finished = Integer.parseInt(strfinished) == 0 ? false : true;
			if (!strdeadline.equals("unknown")) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				deadline = LocalDate.parse(strdeadline, formatter);
			}
			return apManager.edit(id, name, description, finished, deadline);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") long id) {
		return "" + apManager.delete(id, false);
	}
	
	@RequestMapping(value="/delete_all", method=RequestMethod.DELETE)
	public String delete() {
		return "" + apManager.delete(0, true);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody 
	public ActionPoint addActionPoint( HttpServletResponse response,
		@RequestParam(value="name", required = true) String name,
		@RequestParam(value="description", required = false, defaultValue = "None") String description,
		@RequestParam(value="deadline", required = true) String deadline) {
		ActionPoint newPoint = apManager.insert(name, description, deadline);
		if (newPoint != null) {
			response.setHeader("Location", "http://localhost:8080");
			response.setStatus(302);
		}
		else {
			response.setHeader("Location", "http://localhost:8080/special_error?e=Deadline%20impossible:%20Date%20set%20has%20passed.");
			response.setStatus(302);
		}
		return newPoint;
	}
}
