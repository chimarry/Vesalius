package pro.artse.user.util;

import java.time.LocalDateTime;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.user.models.ActivityLog;

public final class Mapper {
	public static final ActivityLog mapFrom(ActivityLogWrapper wrapper) {
		return new ActivityLog(LocalDateTime.parse(wrapper.getLogInAt()), LocalDateTime.parse(wrapper.getLogOutAt()));
	}

	public static final ActivityLogWrapper mapToWrapper(ActivityLog activityLog) {
		LocalDateTime logInAt = LocalDateTime.parse(activityLog.getLogInAt());
		LocalDateTime logOutAt = LocalDateTime.parse(activityLog.getLogOutAt());
		return new ActivityLogWrapper(logInAt, logOutAt);
	}
}
