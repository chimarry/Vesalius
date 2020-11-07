package pro.artse.medicalstaff.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pro.artse.medicalstaff.errorhandling.*;

public final class Mapper {
	public static final <T> MSResultMessage<T> mapFromCR(String resultMessage, Type resultType) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		CustomCrDeserializer<T> deserializer = new CustomCrDeserializer<T>(resultType);
		gsonBuilder.registerTypeAdapter(new TypeToken<MSResultMessage<T>>() {
		}.getType(), deserializer);

		Gson customGson = gsonBuilder.create();
		return customGson.fromJson(resultMessage, new TypeToken<MSResultMessage<T>>() {
		}.getType());
	}

	public static MSStatus mapHttpStatus(String status) {
		switch (status.toUpperCase()) {
		case "Internal_SERVER_ERROR":
			return MSStatus.SERVER_ERROR;
		case "ACCEPTED":
			return MSStatus.SUCCESS;
		case "NO_CONTENT":
			return MSStatus.NOT_FOUND;
		case "FOUND":
			return MSStatus.EXISTS;
		case "BAD_REQUEST":
			return MSStatus.INVALID_DATA;
		default:
			return MSStatus.SERVER_ERROR;
		}
	}
}
