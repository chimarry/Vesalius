package pro.artse.centralr.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pro.arste.common.result.OperationStatus;
import pro.arste.common.result.ResultMessage;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.IActivityLogManager;
import pro.artse.dal.models.ActivityLogDTO;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;

public class ActivityLogManager implements pro.artse.centralr.managers.IActivityLogManager {

	private final IActivityLogManager activityLogManager = ManagerFactory.getActivityLogManager();

	@Override
	public ResultMessage<Boolean> add(ActivityLogWrapper activity, String token) {
		// TODO: check token validity

		ActivityLogDTO item = Mapper.mapToDTO(activity);
		item.setToken(token);

		Boolean isAdded = activityLogManager.add(item);
		if (isAdded)
			return new ResultMessage<Boolean>(isAdded, OperationStatus.SUCCESS);
		else
			return new ResultMessage<Boolean>(isAdded, OperationStatus.SERVER_ERROR);
	}

	@Override
	public List<ActivityLogWrapper> getAll(String token) {
		// TODO: check token validation

		List<ActivityDTO> activities = activityLogManager.getAll(token);
		return activities.stream().map(x -> Mapper.mapToWrapper(x))
				.collect(Collectors.toCollection(ArrayList<ActivityLogWrapper>::new));
	}
}
