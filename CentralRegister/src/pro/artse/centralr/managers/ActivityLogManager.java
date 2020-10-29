package pro.artse.centralr.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.IActivityLogManager;
import pro.artse.dal.models.ActivityLogDTO;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;

public class ActivityLogManager implements pro.artse.centralr.managers.IActivityLogManager {

	private final IActivityLogManager activityLogManager = ManagerFactory.getActivityLogManager();

	@Override
	public CrResultMessage<Boolean> add(ActivityLogWrapper activity, String token) {
		ActivityLogDTO item = Mapper.mapToDTO(activity);
		item.setToken(token);

		DBResultMessage<Boolean> isAdded = activityLogManager.add(item);
		return Mapper.mapFrom(isAdded);
	}

	@Override
	public CrResultMessage<List<ActivityLogWrapper>> getAll(String token) {
		DBResultMessage<List<ActivityDTO>> activities = activityLogManager.getAll(token);
		List<ActivityLogWrapper> wrappedActivities = activities.getResult().stream().map(x -> Mapper.mapToWrapper(x))
				.collect(Collectors.toCollection(ArrayList<ActivityLogWrapper>::new));
		return new CrResultMessage<List<ActivityLogWrapper>>(wrappedActivities, Mapper.mapStatus(activities.getStatus()),
				activities.getMessage());
	}
}
