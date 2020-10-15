package pro.artse.user.centralr.services;

import java.io.IOException;
import java.util.List;

import pro.artse.user.models.ActivityLog;

public interface IActivityLogService {
	List<ActivityLog> getAll(String token) throws IOException;
}
