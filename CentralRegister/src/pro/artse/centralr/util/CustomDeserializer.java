package pro.artse.centralr.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import pro.arste.centralr.errorhandling.CrResultMessage;

public class CustomDeserializer<T> implements JsonDeserializer<CrResultMessage<T>> {

	private Type resultType;

	public CustomDeserializer(Type resultType) {
		super();
		this.resultType = resultType;
	}

	@Override
	public CrResultMessage<T> deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonElement resultElement = jsonObject.get("result");
		String status = jsonObject.get("status").getAsString();
		JsonElement messageElement = jsonObject.get("message");
		String message = null;
		if (messageElement != null)
			message = messageElement.getAsString();
		Gson gson = new Gson();
		T result = (T) gson.fromJson(resultElement, resultType);
		return new CrResultMessage(result, status, message);
	}

}
