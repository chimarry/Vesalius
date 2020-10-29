package pro.artse.centralr.managers;

import java.util.List;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.ActivityLogWrapper;

public interface IActivityLogManager {
	CrResultMessage<Boolean> add(ActivityLogWrapper wrapper, String token);

	CrResultMessage<List<ActivityLogWrapper>> getAll(String token);
}
