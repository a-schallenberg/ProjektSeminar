package main.computervision.imageadapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter {
	private static final String PATH = "resources/images/";
	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;

	public static final ArrayList<TrainData> TRAIN_DATA = new ArrayList<>();

	public static void init() {
		addTrainData("road_sign_0.png", RoadSignLabel.STOP);
		addTrainData("test.png", RoadSignLabel.GIVE_WAY);
		addTrainData("road_sign_1.png", RoadSignLabel.DANGER);
		addTrainData("road_sign_2.png", RoadSignLabel.NO_ENTRANCE);
		addTrainData("road_sign_3.png", RoadSignLabel.CROSS);
		addTrainData("road_sign_4.png", RoadSignLabel.STOP);
		addTrainData("road_sign_5.png", RoadSignLabel.DANGER);
		addTrainData("road_sign_6.png", RoadSignLabel.NO_ENTRANCE);
		addTrainData("road_sign_7.png", RoadSignLabel.NO_ENTRANCE);
		addTrainData("road_sign_8.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_9.png", RoadSignLabel.GIVE_WAY);
		addTrainData("road_sign_10.png", RoadSignLabel.NO_ENTRANCE);
		addTrainData("road_sign_11_hard_to_read.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_12.png", RoadSignLabel.NO_ENTRANCE);
		addTrainData("road_sign_13.png", RoadSignLabel.CROSSWALK);
		addTrainData("road_sign_14.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_15.png", RoadSignLabel.MAIN_ROAD);
		addTrainData("road_sign_16.png", RoadSignLabel.GIVE_WAY);
		addTrainData("road_sign_17.png", RoadSignLabel.GIVE_WAY);
		addTrainData("road_sign_18.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_19.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_20.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_21.png", RoadSignLabel.NO_ENTRANCE);
		addTrainData("road_sign_22.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_23.png", RoadSignLabel.MAIN_ROAD);
		addTrainData("road_sign_24.png", RoadSignLabel.CROSSWALK);
		addTrainData("road_sign_25.png", RoadSignLabel.CROSSWALK);
		addTrainData("road_sign_26.png", RoadSignLabel.SIDE_WALK);
		addTrainData("road_sign_27.png", RoadSignLabel.CROSS);
		addTrainData("road_sign_28.png", RoadSignLabel.CROSS);
		addTrainData("road_sign_29.png", RoadSignLabel.CROSS);
		addTrainData("road_sign_30.png", RoadSignLabel.CROSS);
		addTrainData("road_sign_31.png", RoadSignLabel.MAIN_ROAD);
		addTrainData("road_sign_32.png", RoadSignLabel.MAIN_ROAD);
		addTrainData("road_sign_33.png", RoadSignLabel.MAIN_ROAD);
		addTrainData("road_sign_34.png", RoadSignLabel.MAIN_ROAD);


		System.out.println("[ImageAdapter] Finished initializing. " + TRAIN_DATA.size() + " images loaded");
	}

	private static void addTrainData(String filename, RoadSignLabel label) {
		TRAIN_DATA.add(new TrainData(getRGBs(scaleImage(squareImage(loadImage(filename)))), label));
	}

	private static BufferedImage loadImage(String filename) {
		try {
			return ImageIO.read(new File(PATH + filename));
		} catch(IOException e) {
			throw new IllegalArgumentException(e);
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

	public static double[][] getInput() {
		double[][] input = new double[TRAIN_DATA.size()][WIDTH * HEIGHT];

		for(int i = 0; i < input.length; i++)
			for(int j = 0; j < input[i].length; j++)
				input[i][j] = TRAIN_DATA.get(i).image()[j];

		return input;
	}

	public static double[][] getLabels() {
		double[][] labels = new double[TRAIN_DATA.size()][RoadSignLabel.values().length];

		for(int i = 0; i < labels.length; i++)
			labels[i][TRAIN_DATA.get(i).label().index] = 1d;

		return labels;
	}
}
