package main.computervision.imageadapter;

public enum RoadSignLabel {
	CROSS(0),
	CROSSWALK(1),
	DANGER(2),
	DOUBLE_CURVE(3),
	GIVE_WAY(4),
	MAIN_ROAD(5),
	NO_ENTRANCE(6),
	PROHIBITION(7),
	RIGHT_CURVE(8),
	RIGHT_OF_WAY(9),
	STOP(10);

	public final int index;

	RoadSignLabel(int index) {
		this.index = index;
	}
}
