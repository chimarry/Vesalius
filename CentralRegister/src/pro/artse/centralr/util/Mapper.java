package pro.artse.centralr.util;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.models.LocationWrapper;
import pro.artse.centralr.models.NotificationWrapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.models.ActivityLogDTO;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;
import pro.artse.dal.models.LocationDTO;
import pro.artse.dal.models.NotificationDTO;

/**
 * Maper between layers and tiers.
 * 
 * @author Marija
 *
 */
public class Mapper {
	public static NotificationWrapper mapToWrapper(NotificationDTO dto) {
		LocationWrapper location = Mapper.mapToWrapper(dto.getLocation());
		return new NotificationWrapper(dto.getToken(), dto.getFromWhomToken(), dto.getName(), location);
	}

	public static NotificationDTO mapToDTO(NotificationWrapper wrapper) {
		LocationDTO location = Mapper.mapToDTO(wrapper.getLocation());
		return new NotificationDTO(wrapper.getToken(), wrapper.getFromWhomToken(), location);
	}

	public static ActivityLogDTO mapToDTO(ActivityLogWrapper wrapper) {
		return new ActivityLogDTO(LocalDateTime.parse(wrapper.getLogInAt()),
				LocalDateTime.parse(wrapper.getLogOutAt()));
	}

	public static ActivityLogWrapper mapToWrapper(ActivityDTO dto) {
		return new ActivityLogWrapper(dto.getLogInAt(), dto.getLogOutAt());
	}

	public static LocationDTO mapToDTO(LocationWrapper location) {
		return new LocationDTO(location.getLongitude(), location.getLatitude(),
				LocalDateTime.parse(location.getSince()), LocalDateTime.parse(location.getUntil()));
	}

	public static LocationWrapper mapToWrapper(LocationDTO location) {
		return new LocationWrapper(location.getLongitude(), location.getLatitude(), location.getSince(),
				location.getUntil());
	}

	public static <T> CrResultMessage<T> mapFrom(String resultMessage, Type resultType) {
		CustomDeserializer<T> deserializer = new CustomDeserializer<T>(resultType);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(new CrResultMessage<T>().getClass(), deserializer);

		Gson customGson = gsonBuilder.create();
		return customGson.fromJson(resultMessage, new CrResultMessage<T>().getClass());
	}

	public static <T> CrResultMessage<T> mapFrom(DBResultMessage<T> resultMessage) {
		CrResultMessage<T> mappedResultMessage = new CrResultMessage<T>(mapStatus(resultMessage.getStatus()));
		mappedResultMessage.setResult(resultMessage.getResult());
		mappedResultMessage.setMessage(resultMessage.getMessage());
		return mappedResultMessage;
	}

	public static Status mapStatus(DbStatus status) {
		switch (status) {
		case SERVER_ERROR:
			return Status.INTERNAL_SERVER_ERROR;
		case SUCCESS:
			return Status.OK;
		case NOT_FOUND:
			return Status.NO_CONTENT;
		case EXISTS:
			return Status.FOUND;
		case INVALID_DATA:
			return Status.BAD_REQUEST;
		case UNKNOWN_ERROR:
			return Status.INTERNAL_SERVER_ERROR;
		default:
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	public static Status mapStatus(String status) {
		switch (status) {
		case "SERVER_ERROR":
			return Status.INTERNAL_SERVER_ERROR;
		case "SUCCESS":
			return Status.OK;
		case "NOT_FOUND":
			return Status.NO_CONTENT;
		case "EXISTS":
			return Status.FOUND;
		case "INVALID_DATA":
			return Status.BAD_REQUEST;
		case "UNKNOWN_ERROR":
			return Status.INTERNAL_SERVER_ERROR;
		default:
			return Status.INTERNAL_SERVER_ERROR;
		}
	}
}
