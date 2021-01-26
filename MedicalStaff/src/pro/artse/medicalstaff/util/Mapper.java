package pro.artse.medicalstaff.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.models.BasicFileInfo;
import pro.artse.medicalstaff.errorhandling.*;
import pro.artse.medicalstaff.models.MedicalDocument;

public final class Mapper {
	public static final MedicalDocument mapFrom(BasicFileInfo file) {
		return new MedicalDocument(file.getFileName(), file.getSizeInBytes(), file.getSavedOn());
	}

	public static final <T> MSResultMessage<T> mapFromFS(FSResultMessage<T> original) {
		MSResultMessage<T> copy = new MSResultMessage<T>();
		copy.setResult(original.getResult());
		copy.setMessage(original.getMessage());
		copy.setStatus(mapFSStatus(original.getStatus().toString()));
		return copy;
	}

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

	public static MSStatus mapFSStatus(String status) {
		switch (status.toUpperCase()) {
		case "SERVER_ERROR":
			return MSStatus.SERVER_ERROR;
		case "SUCCESS":
			return MSStatus.SUCCESS;
		case "NOT_FOUND":
			return MSStatus.NOT_FOUND;
		case "EXISTS":
			return MSStatus.EXISTS;
		case "INVALID_DATA":
			return MSStatus.INVALID_DATA;
		default:
			return MSStatus.SERVER_ERROR;
		}
	}
}
