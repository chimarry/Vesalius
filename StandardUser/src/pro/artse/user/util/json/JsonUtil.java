package pro.artse.user.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.*;

public class JsonUtil {
	/**
	 * Parses java string as JSON array.
	 * 
	 * @param reader Opened reader.
	 * @return Result of method.
	 * @throws IOException
	 */
	public static JsonArray readJsonList(BufferedReader reader) throws IOException {
		String resultString = reader.readLine();
		JsonParser parser = new JsonParser();
		JsonElement elem = parser.parse(resultString);
		JsonArray array = elem.getAsJsonArray();
		return array;
	}

	/**
	 * Parses java string as JSON object.
	 * 
	 * @param reader Opened reader.
	 * @return Result of method.
	 * @throws IOException
	 */
	public static JsonObject readJsonObject(BufferedReader reader) throws IOException {
		String resultString = reader.readLine();
		JsonParser parser = new JsonParser();
		JsonElement elem = parser.parse(resultString);
		JsonObject object = elem.getAsJsonObject();
		return object;
	}

	/**
	 * Maps object to JSON.
	 * 
	 * @param <T>     Type of the element.
	 * @param element Element to map.
	 * @return JSON string.
	 */
	public static <T> String mapToJson(T element) {
		Gson gson = new Gson();
		return gson.toJson(element);
	}

	/**
	 * Maps object to JSON.
	 * 
	 * @param <T>     Type of the element.
	 * @param element Element to map.
	 * @return JSON string.
	 */
	public static <T> String mapToJson(T element, JsonSerializer<T> serializer, Class<T> t) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(t, serializer);
		Gson gson = builder.create();
		return gson.toJson(element);
	}

	public static <T> ArrayList<T> deserialize(JsonArray array, Class resultType) {
		Gson gson = new Gson();
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i < array.size(); ++i)
			list.add((T) gson.fromJson(array.get(i), resultType));
		return list;
	}
}
