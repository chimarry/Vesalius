package pro.artse.medicalstaff.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import pro.artse.medicalstaff.errorhandling.MSResultMessage;

public class CustomCrDeserializer<T> implements JsonDeserializer<MSResultMessage<T>> {

	private Type resultType;

	public CustomCrDeserializer(Type resultType) {
		super();
		this.resultType = resultType;
	}

	@Override
	public MSResultMessage<T> deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		String status = jsonObject.get("httpStatusCode").getAsString();
		JsonElement messageElement = jsonObject.get("message");
		String message = null;
		if (messageElement != null)
			message = messageElement.getAsString();

		Gson gson = new Gson();
		JsonElement resultJson = jsonObject.get("result");
		T resultElementRaw = (T) gson.fromJson(resultJson, resultType);
		return new MSResultMessage<T>(resultElementRaw, Mapper.mapHttpStatus(status), message);
	}
}
