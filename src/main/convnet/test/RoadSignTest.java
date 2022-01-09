package main.convnet.test;

import main.convnet.ConvNet;

import java.util.function.Function;

import static main.convnet.test.RoadSignLabel.*;

public class RoadSignTest {
	private static final int    IMAGE_SIZE    = 128;
	private static final int[]  HIDDEN_LAYERS = {32, 32, 32, 32};
	private static final double LEARN_RATE    = 0.12;
	private static final int    ITERATIONS    = 10000;

	public static void main(String[] args) {
		testWithBackground();
		System.out.println("\n");
		testWithoutBackground();
	}

	private static void test(String imgFolder) {
		ConvNet net = new ConvNet("resources/images/" + imgFolder + "/", IMAGE_SIZE, IMAGE_SIZE, RoadSignLabel.values().length, HIDDEN_LAYERS);

		System.out.println("Start loading images");
		long start = System.currentTimeMillis();

		net.addTrainData("road_sign_0.png", STOP.getTarget());
		net.addTrainData("road_sign_1.png", DANGER.getTarget());
		net.addTrainData("road_sign_2.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_3.png", CROSS.getTarget());
		net.addTrainData("road_sign_4.png", STOP.getTarget());
		net.addTrainData("road_sign_5.png", DANGER.getTarget());
		net.addTrainData("road_sign_6.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_7.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_8.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_9.png", GIVE_WAY.getTarget());
		net.addTrainData("road_sign_10.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_11_hard_to_read.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_12.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_13.png", CROSSWALK.getTarget());
		net.addTrainData("road_sign_14.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_15.png", MAIN_ROAD.getTarget());
		net.addTrainData("road_sign_16.png", GIVE_WAY.getTarget());
		net.addTrainData("road_sign_17.png", GIVE_WAY.getTarget());
		net.addTrainData("road_sign_18.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_19.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_20.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_21.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_22.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_23.png", MAIN_ROAD.getTarget());
		net.addTrainData("road_sign_24.png", CROSSWALK.getTarget());
		net.addTrainData("road_sign_25.png", CROSSWALK.getTarget());
		net.addTrainData("road_sign_26.png", SIDE_WALK.getTarget());
		net.addTrainData("road_sign_27.png", CROSS.getTarget());
		net.addTrainData("road_sign_28.png", CROSS.getTarget());
		net.addTrainData("road_sign_29.png", CROSS.getTarget());
		net.addTrainData("road_sign_30.png", CROSS.getTarget());
		net.addTrainData("road_sign_31.png", MAIN_ROAD.getTarget());
		net.addTrainData("road_sign_32.png", MAIN_ROAD.getTarget());
		net.addTrainData("road_sign_33.png", MAIN_ROAD.getTarget());
		net.addTrainData("road_sign_34.png", MAIN_ROAD.getTarget());
		net.addTrainData("road_sign_35.png", STOP.getTarget());
		net.addTrainData("road_sign_36.png", STOP.getTarget());
		net.addTrainData("road_sign_37.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_38.png", NO_ENTRANCE.getTarget());
		net.addTrainData("road_sign_39_multiple_signs.png", CROSSWALK.getTarget());
		net.addTrainData("road_sign_40.png", CROSSWALK.getTarget());
		net.addTrainData("road_sign_41.png", CROSSWALK.getTarget());
		net.addTrainData("road_sign_42.png", GIVE_WAY.getTarget());

		System.out.println("Finished loading images in " + (System.currentTimeMillis() - start) + "ms");

		net.train(LEARN_RATE, ITERATIONS);
	}

	private static void testWithBackground() {
		System.out.println("Test with background:");
		test("background");
	}

	private static void testWithoutBackground() {
		System.out.println("Test without background:");
		test("nobackground");
	}
}
