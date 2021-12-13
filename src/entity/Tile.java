package entity;

import component.Entity;
import component.Sprite;

public class Tile extends Entity {

	public Tile(double x, double y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public Tile(double x, double y, int r) {
		super(x, y, r);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public Sprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//@Override
	/*public void draw() {
		
	}*/

}
