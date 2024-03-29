package pro.artse.user.util.json;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.util.Mapper;

public class CustomCrDeserializer<T> implements JsonDeserializer<SUResultMessage<T>> {

	private Type resultType;

	public CustomCrDeserializer(Type resultType) {
		super();
		this.resultType = resultType;
	}

	@Override
	public SUResultMessage<T> deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		try {
			JsonObject jsonObject = json.getAsJsonObject();
			String status = jsonObject.get("httpStatusCode").getAsString();
			JsonElement messageElement = jsonObject.get("message");
			String message = null;
			if (messageElement != null)
				message = messageElement.getAsString();

			Gson gson = new Gson();
			JsonElement resultJson = jsonObject.get("result");
			T resultElementRaw = (T) gson.fromJson(resultJson, resultType);
			return new SUResultMessage<T>(resultElementRaw, Mapper.mapHttpStatus(status), message);
		} catch (IllegalStateException | JsonSyntaxException e) {
			ErrorHandler.handle(e);
			return new SUResultMessage<T>(null, SUStatus.SERVER_ERROR, "Connection with Central register failed.");
		}
	}
}
