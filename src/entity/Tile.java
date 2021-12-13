package entity;

import component.Entity;
import component.Sprite;

public class Tile extends Entity {
	
	private double upperBound;
	private double lowerBound;
	private double rightBound;
	private double leftBound;
	

	public Tile(double x, double y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
		upperBound = y;
		lowerBound = y+h;
		leftBound = x;
		rightBound = x+w;
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
	
	public double getUpperBound() {
		return upperBound;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public double getRightBound() {
		return rightBound;
	}

	public double getLeftBound() {
		return leftBound;
	}

	
	//@Override
	/*public void draw() {
		
	}*/

}
