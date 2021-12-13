package logic;

import entity.Tile;

public class TileGenerator {

	public TileGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public static void generate() {
		SceneManager.getInstance().addTile(new Tile(0, 670, 3200, 50));
		SceneManager.getInstance().addTile(new Tile(600, 540, 3200, 50));
	}

}
