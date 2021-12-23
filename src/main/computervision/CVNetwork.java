package main.computervision;

import main.Network;
import main.computervision.imageadapter.ImageAdapter;
import main.computervision.imageadapter.RoadSignLabel;

public class CVNetwork {
	private static Network network;

	public static void init() {
		int input = ImageAdapter.WIDTH * ImageAdapter.HEIGHT;
		int output = RoadSignLabel.values().length;
		network = new Network(input, output, 16, 16);
	}

	public static void train() {
		network.train(ImageAdapter.getInput(), ImageAdapter.getLabels(), 0.8, 10000);
	}

	public static void main(String[] args) {
		ImageAdapter.init();
		init();
		train();
	}
}
