package entity;

import component.Entity;
import component.Sprite;
import javafx.scene.canvas.GraphicsContext;
import logic.SceneManager;

public class Background extends Entity {
	// Position
	public static double x = 0;
	public static double y = 0;
	public static final int width = 1280;
	public static final int height = 720;

	// Images
	private static Sprite backgroundSprite;

	public Background() {
		// TODO Auto-generated constructor stub
		super(x,y,1280,720);
		backgroundSprite = new Sprite("background-sky.png");
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sprite getImage() {
		// TODO Auto-generated method stub
		return backgroundSprite;
	}
	
	@Override
	public void draw(GraphicsContext gc,boolean f) {
		super.draw(gc, getImage().getImage(), SceneManager.getInstance().getOffsetX(), SceneManager.getInstance().getOffsetY(), width, height);
	}

}
