package pro.artse.dal.factory;

import pro.artse.dal.managers.*;
import pro.artse.dal.managers.redis.*;

/**
 * @author Marija Represents dependency factory, that is responsible for
 *         creating managers that work with different type of databases.
 */
public final class ManagerFactory {

	/**
	 * @return Manager for manipulating user's activities in application
	 * @see ActivityLogManager
	 */
	public static IActivityLogManager getActivityLogManager() {
		return new ActivityLogManager();
	}

	/**
	 * @return Manager for manipulating information about user.
	 * @see UserManager
	 */
	public static IUserManager getUserManager() {
		return new UserManager();
	}

	/**
	 * @return Manager for manipulating locations.
	 * @see LocationManager
	 */
	public static ILocationManager getLocationManager() {
		return new LocationManager();
	}

	/**
	 * @return Manager for manipulating notifications.
	 * @see NotificationManager
	 * @return
	 */
	public static INotificationManager getNotificationManager() {
		return new NotificationManager();
	}
}
