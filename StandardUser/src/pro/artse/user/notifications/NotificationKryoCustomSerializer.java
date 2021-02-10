package pro.artse.user.notifications;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pro.artse.user.models.Location;
import pro.artse.user.models.Notification;

public class NotificationKryoCustomSerializer extends com.esotericsoftware.kryo.Serializer<Notification> {

	@Override
	public Notification read(Kryo kryo, Input in, Class<Notification> arg2) {
		kryo.register(Notification.class);
		kryo.register(Location.class, new LocationKryoCustomSerializer());
		Notification notification = new Notification();
		notification.setName(in.readString());
		notification.setToken(in.readString());
		notification.setFromWhomToken(in.readString());
		notification.setLocation(kryo.readObject(in, Location.class));
		return notification;
	}

	@Override
	public void write(Kryo kryo, Output out, Notification notification) {
		kryo.register(Notification.class);
		kryo.register(Location.class, new LocationKryoCustomSerializer());
		out.writeString(notification.getName());
		out.writeString(notification.getToken());
		out.writeString(notification.getFromWhomToken());
		kryo.writeObject(out, notification.getLocation());
	}

}
