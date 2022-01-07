package main.convnet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageAdapter {
	private final String path; // "resources/images/"
	public final int imgWidth; // 64
	public final int imgHeight; // 64

	public ImageAdapter(String path, int imgWidth, int imgHeight) {
		this.path = path;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}

	public int[] getImageRGBs(String filename) {
		return getRGBs(scaleImage(squareImage(loadImage(filename))));
	}

	private BufferedImage loadImage(String filename) {
		try {
			return ImageIO.read(new File(path + filename));
		} catch(IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static int[] getRGBs(BufferedImage img) {
		int width = img.getWidth(), height = img.getHeight();
		int[] array = new int[width * height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				array[width * y + x] = img.getRGB(x, y);

		return array;
	}

	private static BufferedImage squareImage(BufferedImage img) {
		int width = img.getWidth(), height = img.getHeight();
		if(width > height) {
			int x = (width - height) / 2;
			return img.getSubimage(x, 0, height, height);
		}
		else if(width < height) {
			int y = (height - width) / 2;
			return img.getSubimage(0, y, width, width);
		}
		else
			return img;
	}

	private BufferedImage scaleImage(BufferedImage img) {
		return convertToBufferedImage(img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT));
	}

	private static BufferedImage convertToBufferedImage(Image image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}
}
