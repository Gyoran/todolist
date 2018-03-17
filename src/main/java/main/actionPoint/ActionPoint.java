package main.actionPoint;

import javax.persistence.*;
import java.time.LocalDate;
//import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.Gson;

@Entity
public class ActionPoint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length = 30)
	private String name;
	@Column(nullable = true, length = 100)
	private String description;
	@Column
	private boolean finished;
	@Column
	private LocalDate deadline;
	
	
	public ActionPoint() {
		
	}
	
	public ActionPoint(String name, String description, LocalDate deadline) {
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.finished = false;
	}
	
	//getters
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean getFinished() {
		return finished;
	}
	
	public LocalDate getDeadline() {
		return deadline;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	
	@Override
	public String toString() {
		Gson jsonWriter = new Gson();
		return jsonWriter.toJson(this);
	}
}
