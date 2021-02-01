package pro.artse.user.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.zip.DataFormatException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.models.LocationWrapper;
import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.models.BasicFileInfo;
import pro.artse.fileserver.util.Compressor;
import pro.artse.user.errorhandling.*;
import pro.artse.user.models.ActivityLog;
import pro.artse.user.models.Location;
import pro.artse.user.models.MedicalDocument;
import pro.artse.user.util.json.*;

public final class Mapper {
	public static final ActivityLog mapFrom(ActivityLogWrapper wrapper) {
		return new ActivityLog(LocalDateTime.parse(wrapper.getLogInAt()), LocalDateTime.parse(wrapper.getLogOutAt()));
	}

	public static final MedicalDocument mapFrom(BasicFileInfo file) {
		return new MedicalDocument(file.getFileName(), file.getSizeInBytes(), file.getSavedOn());
	}

	public static final ActivityLogWrapper mapToWrapper(ActivityLog activityLog) {
		LocalDateTime logInAt = LocalDateTime.parse(activityLog.getLogInAt());
		LocalDateTime logOutAt = LocalDateTime.parse(activityLog.getLogOutAt());
		return new ActivityLogWrapper(logInAt, logOutAt);
	}

	public static final <T> SUResultMessage<T> mapFromCR(String resultMessage, Type resultType) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		CustomCrDeserializer<T> deserializer = new CustomCrDeserializer<T>(resultType);
		gsonBuilder.registerTypeAdapter(new TypeToken<SUResultMessage<T>>() {
		}.getType(), deserializer);

		Gson customGson = gsonBuilder.create();
		return customGson.fromJson(resultMessage, new TypeToken<SUResultMessage<T>>() {
		}.getType());
	}

	public static final <T> SUResultMessage<T> mapFromFS(FSResultMessage<T> original) {
		SUResultMessage<T> copy = new SUResultMessage<T>();
		copy.setResult(original.getResult());
		copy.setMessage(original.getMessage());
		copy.setStatus(mapFSStatus(original.getStatus().toString()));
		return copy;
	}

	public static final <T> SUResultMessage<T> mapFromTs(String resultMessage, Type resultType) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		CustomTsDeserializer<T> deserializer = new CustomTsDeserializer<T>(resultType);
		gsonBuilder.registerTypeAdapter(new TypeToken<SUResultMessage<T>>() {
		}.getType(), deserializer);

		Gson customGson = gsonBuilder.create();
		return customGson.fromJson(resultMessage, new TypeToken<SUResultMessage<T>>() {
		}.getType());
	}

	public static LocationWrapper mapToWrapper(Location location) {
		return new LocationWrapper(location.getLongitude(), location.getLatitude(), location.getSince(),
				location.getUntil());
	}

	public static SUStatus mapHttpStatus(String status) {
		switch (status.toUpperCase()) {
		case "INTERNAL_SERVER_ERROR":
			return SUStatus.SERVER_ERROR;
		case "OK":
		case "ACCEPTED":
			return SUStatus.SUCCESS;
		case "NO_CONTENT":
			return SUStatus.NOT_FOUND;
		case "FOUND":
			return SUStatus.EXISTS;
		case "BAD_REQUEST":
			return SUStatus.INVALID_DATA;
		default:
			return SUStatus.SERVER_ERROR;
		}
	}

	public static SUStatus mapTsStatus(String status) {
		switch (status.toUpperCase()) {
		case "SERVER_ERROR":
			return SUStatus.SERVER_ERROR;
		case "SUCCESS":
			return SUStatus.SUCCESS;
		case "NOT_FOUND":
			return SUStatus.NOT_FOUND;
		case "EXISTS":
			return SUStatus.EXISTS;
		case "INVALID_DATA":
			return SUStatus.INVALID_DATA;
		default:
			return SUStatus.SERVER_ERROR;
		}
	}

	public static SUStatus mapFSStatus(String status) {
		switch (status.toUpperCase()) {
		case "SERVER_ERROR":
			return SUStatus.SERVER_ERROR;
		case "SUCCESS":
			return SUStatus.SUCCESS;
		case "NOT_FOUND":
			return SUStatus.NOT_FOUND;
		case "EXISTS":
			return SUStatus.EXISTS;
		case "INVALID_DATA":
			return SUStatus.INVALID_DATA;
		default:
			return SUStatus.SERVER_ERROR;
		}
	}
}