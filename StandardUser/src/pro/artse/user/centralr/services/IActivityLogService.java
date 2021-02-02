package pro.artse.user.centralr.services;

import java.io.IOException;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.ActivityLog;

public interface IActivityLogService {
	SUResultMessage<ActivityLog[]> getAll(String token) throws IOException;

	SUResultMessage<Boolean> add(ActivityLog activityLog, String token) throws IOException;
}
