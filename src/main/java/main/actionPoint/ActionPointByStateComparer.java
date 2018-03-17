package main.actionPoint;

import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
public class ActionPointByStateComparer implements Comparator<ActionPoint>{
	
	@Override
	public int compare(ActionPoint ap1, ActionPoint ap2) {	
		Boolean b1 = ap1.getFinished();
		Boolean b2 = ap2.getFinished();
		return b1.compareTo(b2);
	}
}
