package pro.artse.dal.managers;

import java.util.List;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.models.ActivityLogDTO;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;

public interface IActivityLogManager {

	DBResultMessage<Boolean> add(ActivityLogDTO userActivity);

	DBResultMessage<List<ActivityDTO>> getAll(String token);

	DBResultMessage<Boolean> deleteAll(String token);
}
