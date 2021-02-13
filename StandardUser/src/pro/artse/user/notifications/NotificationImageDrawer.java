package pro.artse.user.notifications;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.models.Location;
import pro.artse.user.models.Notification;

public class NotificationImageDrawer {
	public static void drawText(Notification notification) {
		String directoryPath = NotificationStorage.buildDirectoryForImagesPath(notification.getToken());

		Location location = notification.getLocation();

		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();
		Font font = new Font("Arial", Font.PLAIN, 10);
		g2d.setFont(font);
		g2d.setBackground(Color.BLACK);
		int width = 200;
		int height = 100;
		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		g2d.setBackground(Color.BLACK);
		g2d.setColor(Color.RED);
		g2d.drawString("Longitude: " + location.getLongitude(), 10, 20);
		g2d.drawString("Latitude: " + location.getLatitude(), 10, 40);
		g2d.dispose();
		try {
			ImageIO.write(img, "png", new File(directoryPath + File.separator + notification.getName() + ".png"));
		} catch (IOException ex) {
			ErrorHandler.handle(ex);
		}
	}

	public static Image readImageForNotification(Notification notification) {
		String directoryPath = NotificationStorage.buildDirectoryForImagesPath(notification.getToken());
		File file = new File(directoryPath + File.separator + notification.getName() + ".png");
		return new Image(file.toURI().toString());
	}
}
