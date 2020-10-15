package pro.artse.user.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.rpc.encoding.Deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

	public static <T> ArrayList<T> deserialize(JsonArray array, Class resultType) {
		Gson gson = new Gson();
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i < array.size(); ++i)
			list.add((T) gson.fromJson(array.get(i), resultType));
		return list;
	}
}
