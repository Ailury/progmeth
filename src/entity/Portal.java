package entity;

import component.Collidable;
import component.Entity;
import component.Sprite;
import javafx.scene.canvas.GraphicsContext;
import logic.SceneManager;

public class Portal extends Entity implements Collidable {

	private Sprite portal;
	
	public Portal() {
		super(1280*2-200, SceneManager.getGround()-150, 90, 150);
		// TODO Auto-generated constructor stub
		portal = new Sprite("sprite/checkpoint/portal_end.gif");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		checkCollide();
	}

	@Override
	public Sprite getImage() {
		// TODO Auto-generated method stub
		return portal;
	}

	@Override
	public void checkCollide() {
		// TODO Auto-generated method stub
		if(collideWith(SceneManager.getInstance().getPlayer())) {
			
		}
	}

}
