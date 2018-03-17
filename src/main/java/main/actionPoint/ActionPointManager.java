package main.actionPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ActionPointManager {

	@Autowired
	ActionPointDao actionPointDao;
	
	public ActionPoint edit(long id, String name, String description, Boolean finished, LocalDate deadline) {
		ActionPoint actionPoint = actionPointDao.findById(id).orElse(null);
		if (actionPoint == null)
			return actionPoint;
		if (name != null)
			actionPoint.setName(name);
		if (description != null)
			actionPoint.setDescription(description);
		if (finished != null)
			actionPoint.setFinished(finished);
		if (deadline != null)
			actionPoint.setDeadline(deadline);
		try {
			actionPointDao.save(actionPoint);
			return actionPoint;
		}
		catch(DataAccessException ex) {
			return null;
		}
	}
	
	public ActionPoint insert(String name, String description, String deadline) {
		if ((name.length() > 25) || !deadline.matches("^\\d{2}-\\d{2}-\\d{4}$") || description.length() > 800)
			return null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate ld = LocalDate.parse(deadline, formatter);
			if (ld.compareTo(LocalDate.now()) < 0)
				return null;
			ActionPoint actionPoint = new ActionPoint(name, description, ld);
			actionPointDao.save(actionPoint);
			return actionPoint;
		}
		catch(DataAccessException ex) {
			return null;
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	public boolean delete(long id, boolean all) {
		try {
			if (all)
				actionPointDao.deleteAll();
			else
				actionPointDao.deleteById(id);
			return true;
		}
		catch(DataAccessException ex) {
			return false;
		}
	}
	
}
