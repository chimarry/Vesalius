package pro.artse.centralr.api;

public final class ApiPaths {
	public static final String API_PREFIX = "http://localhost:8080/CentralRegister/api/v0.1/";
	public static final String GET_ALL_ACTIVITIES = API_PREFIX + "activities";
	public static final String POST_ACTIVITY = API_PREFIX + "activities";
	public static final String DELETE_UNREGISTER = API_PREFIX + "users";
	public static final String GET_ALL_USERS = API_PREFIX + "users";
	public static final String GET_ONE_USER = API_PREFIX + "users" + "/{token}";
	public static final String DELETE_BLOCK_USER = API_PREFIX + "users" + "/{token}";
	public static final String POST_LOCATION = API_PREFIX + "locations";
	public static final String GET_LOCATIONS = API_PREFIX + "locations";
	public static final String GET_USER_LOCATIONS = GET_ONE_USER + "/locations";
	public static final String POST_USER_IS_INFECTED = GET_ONE_USER + "/infected";
	public static final String GET_NOTIFICATIONS = API_PREFIX + "/notifications";
}
