package pro.artse.user.notifications;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pro.artse.user.models.Location;

public class LocationKryoCustomSerializer extends com.esotericsoftware.kryo.Serializer<Location> {

	@Override
	public Location read(Kryo kryo, Input in, Class<Location> arg2) {
		Location location = new Location();
		location.setSince(in.readString());
		location.setUntil(in.readString());
		location.setLatitude(in.readDouble());
		location.setLongitude(in.readDouble());
		return location;
	}

	@Override
	public void write(Kryo kryo, Output out, Location location) {
		out.writeString(location.getSince());
		out.writeString(location.getUntil());
		out.writeDouble(location.getLatitude());
		out.writeDouble(location.getLongitude());
	}

}
