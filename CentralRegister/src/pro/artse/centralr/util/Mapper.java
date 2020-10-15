package pro.artse.centralr.util;

import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.dal.models.ActivityLogDTO;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;

/**
 * Maper between layers and tiers.
 * 
 * @author Marija
 *
 */
public class Mapper {

	public static ActivityLogDTO mapToDTO(ActivityLogWrapper wrapper) {
		return new ActivityLogDTO(wrapper.getLogInAt(), wrapper.getLogOutAt());
	}

	public static ActivityLogWrapper mapToWrapper(ActivityDTO dto) {
		return new ActivityLogWrapper(dto.getLogInAt(), dto.getLogOutAt());
	}
}
