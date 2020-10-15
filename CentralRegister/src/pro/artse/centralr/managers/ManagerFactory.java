package pro.artse.centralr.managers;

/**
 * Resolves dependencies
 * 
 * @author Marija
 *
 */
public final class ManagerFactory {

	/**
	 * Creates implementation for IActivityLogManager.
	 * 
	 * @return @see ActivityLogManager
	 */
	public static IActivityLogManager getActivityLogManager() {
		return new ActivityLogManager();
	}
}
