package pro.artse.user.util.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.util.Mapper;

public class CustomTsDeserializer<T> implements JsonDeserializer<SUResultMessage<T>> {

	private Type resultType;

	public CustomTsDeserializer(Type resultType) {
		super();
		this.resultType = resultType;
	}

	@Override
	public SUResultMessage<T> deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		String status = jsonObject.get("status").getAsString();
		JsonElement messageElement = jsonObject.get("message");
		String message = null;
		if (messageElement != null)
			message = messageElement.getAsString();

		Gson gson = new Gson();
		JsonElement resultJson = jsonObject.get("result");
		T resultElementRaw = (T) gson.fromJson(resultJson, resultType);
		return new SUResultMessage<T>(resultElementRaw, Mapper.mapTsStatus(status), message);
	}
}
