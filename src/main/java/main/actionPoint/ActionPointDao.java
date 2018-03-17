package main.actionPoint;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ActionPointDao extends CrudRepository<ActionPoint, Long> {
	public List<ActionPoint> findAll();
}
