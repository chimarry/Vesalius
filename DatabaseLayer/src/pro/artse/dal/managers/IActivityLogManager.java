package pro.artse.dal.managers;

import java.util.List;

import pro.artse.dal.models.ActivityLogDTO;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;

public interface IActivityLogManager {
	boolean add(ActivityLogDTO userActivity);

	List<ActivityDTO> getAll(String token);
}
