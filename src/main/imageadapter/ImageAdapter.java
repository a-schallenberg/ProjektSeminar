package main.imageadapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter {
	private static final String PATH = "resources/images/road_sign_";
	private static final int WIDTH = 64;
	private static final int HEIGHT = 64;

	public static final ArrayList<int[]> rgbValues = new ArrayList<>();

	public static void init() {
		for(int i = 0; true; i++) {
			try {
				System.out.println(i);
				BufferedImage img = ImageIO.read(new File(PATH + i + ".png"));

				BufferedImage imgCropped = squareImage(img);
				File imageCropped =  new File(PATH + "imageCropped.png");
				ImageIO.write(imgCropped, "png", imageCropped);

				BufferedImage imgScaled = scaleImage(imgCropped);
				File imageScaled =  new File(PATH + "imageScaled.png");
				ImageIO.write(imgScaled, "png", imageScaled);

				rgbValues.add(getRGBs(img));
			} catch(IOException e) {
				System.out.println("[ImageAdapter] Finished initializing");
				break;
			}
		}
	}

	private static int[] getRGBs(BufferedImage img) {
		int width = img.getWidth(), height = img.getHeight();
		int[] array = new int[width * height];
		for(int y = 0; y <height; y++)
			for(int x = 0; x < width; x++)
				array[width * y + x] = img.getRGB(x, y);

		return array;
	}

	private static BufferedImage squareImage(BufferedImage img) {
		int width = img.getWidth(), height = img.getHeight();
		if(width > height) {
			int x = (width - height) / 2;
			return img.getSubimage(x, 0, height, height);
		} else if(width < height) {
			int y = (height - width) / 2;
			return img.getSubimage(0, y, width, width);
		} else
			return img;
	}

	private static BufferedImage scaleImage(BufferedImage img) {
		return convertToBufferedImage(img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
	}

	private static BufferedImage convertToBufferedImage(Image image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}
}
