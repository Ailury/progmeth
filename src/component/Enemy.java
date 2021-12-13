package component;

import javafx.scene.canvas.GraphicsContext;

public class Enemy extends Entity implements Collidable{

	public Enemy(double x, double y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public Enemy(double x, double y, int r) {
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
	
	@Override
	public void draw(GraphicsContext gc,boolean f) {
		
	}

	@Override
	public void checkCollide() {
		// TODO Auto-generated method stub
		
	}

}
