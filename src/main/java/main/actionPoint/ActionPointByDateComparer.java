package main.actionPoint;

import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
public class ActionPointByDateComparer implements Comparator<ActionPoint>{

	@Override
	public int compare(ActionPoint ap1, ActionPoint ap2) {	
		return ap1.getDeadline().compareTo(ap2.getDeadline());
	}
}
