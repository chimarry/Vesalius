package pro.artse.centralr.managers;

import java.util.List;

import pro.arste.common.result.ResultMessage;
import pro.artse.centralr.models.ActivityLogWrapper;

public interface IActivityLogManager {
	ResultMessage<Boolean> add(ActivityLogWrapper wrapper, String token);

	List<ActivityLogWrapper> getAll(String token);
}
