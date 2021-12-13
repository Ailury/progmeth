package logic;

import entity.Tile;

public class TileGenerator {

	public TileGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public void generate() {
		SceneManager.getInstance().addTile(new Tile(0, 670, 3200, 50));
	}

}
