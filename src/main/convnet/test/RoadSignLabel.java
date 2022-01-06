package main.convnet.test;

public enum RoadSignLabel {
	CROSS(0), 		//Andreas Kreuz 5
	CROSSWALK(1),		//Zebrastreifen 6
	DANGER(2),		//Gefahrenstelle 2
	GIVE_WAY(3),		//Vorfahrtgewähren	5
	MAIN_ROAD(4),		//Hauptstraße	6
	NO_ENTRANCE(5),	//Verbot der Einfahrt	8
	SIDE_WALK(6),		//Fußgängerweg	8
	STOP(7);			//Stop	4

	public final int index;

	RoadSignLabel(int index) {
		this.index = index;
	}

	public int[] getTarget() {
		int[] vec = new int[RoadSignLabel.values().length];
		vec[index] = 1;
		return vec;
	}
}
