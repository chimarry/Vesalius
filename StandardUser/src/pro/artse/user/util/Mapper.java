package pro.artse.user.util;

import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.user.models.ActivityLog;

public final class Mapper {
	public static final ActivityLog mapFrom(ActivityLogWrapper wrapper) {
		return new ActivityLog(wrapper.getLogInAt(), wrapper.getLogOutAt());
	}
}
