package entity;

import java.util.ArrayList;
import java.util.Random;

import component.Collidable;
import component.Entity;
import component.Sprite;
import logic.SceneManager;

public class powerup extends Entity implements Collidable{
	
	private ArrayList<Sprite> sprites;

	public powerup(double x) {
		super(x, SceneManager.getGround()-50,50,50);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		checkCollide();
	}

	@Override
	public Sprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void generate2() {
		Random rand = new Random();
		int rand1 = rand.nextInt(600)+500;
		int rand2 = rand.nextInt(600)+500;
		SceneManager.getInstance().getCollidable().add(new powerup(rand1));
	}

	@Override
	public void checkCollide() {
		// TODO Auto-generated method stub
		if(collideWith(SceneManager.getInstance().getPlayer())) {
			SceneManager.getInstance().getPlayer().changeHp(20);
		}
	}

}
