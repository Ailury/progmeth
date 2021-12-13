package logic;

import java.io.Serializable;
import java.util.ArrayList;

import component.Collidable;
import component.Enemy;
import component.Entity;
import component.Interactable;
import entity.Background;
import entity.Player;
import entity.Portal;
import entity.Powerup;
import entity.TileBackground;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SceneManager extends Canvas implements Serializable {

	private static SceneManager instance = null;
	private double offsetX;
	private double offsetY;
	
	private ArrayList<Enemy> enemy;
	private ArrayList<Entity> props;
	private ArrayList<Collidable> collidable;
	private ArrayList<Interactable> interactable;
	private Player player;
	
	private static final int ground = 670;
	private int level;
	
	private static final double leftBound = 0;
	private static final double rightBound = 1280;
	
	private SceneManager() {
		// TODO Auto-generated constructor stub
		super(1280, 720);
		
		enemy = new ArrayList<Enemy>();
		props = new ArrayList<Entity>();
		collidable = new ArrayList<Collidable>();
		interactable = new ArrayList<Interactable>();
		player = new Player();
		level = 0;
		offsetX = 0;
		offsetY = 0;
		
		gameStart();
	}
	
	public static SceneManager getInstance() {
		if(instance == null)instance = new SceneManager();
		return instance;
	}
	
	public void update() {
		for(Enemy e : enemy) {
			e.update();
		}
		for(Entity e : props) {
			e.update();
		}
		for(Collidable c : collidable) {
			if(c instanceof Entity) ((Entity) c).update();
		}
		for(Interactable i : interactable) {
			if(i instanceof Entity) ((Entity) i).update();
		}
		player.update();
		draw();
	}
	
	public void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.RED);
		for(Enemy e : enemy) {
			e.draw(gc,false);
		}
		for(Entity e : props) {
			e.draw(gc,false);
		}
		for(Collidable c : collidable) {
			if(c instanceof Entity) ((Entity) c).draw(gc,false);
		}
		for(Interactable i : interactable) {
			if(i instanceof Entity) ((Entity) i).draw(gc,false);
		}
		player.draw(gc,false);
	}
	
	public void startlLevel() {
		
	}
	
	public void gameStart() {
		props.add(new Background());
		props.add(new TileBackground());
		props.add(new Portal());
		Powerup.setUp();
		Powerup.generate2();
	}


	public double getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	public ArrayList<Enemy> getEnemy() {
		return enemy;
	}

	public Player getPlayer() {
		return player;
	}

	public static int getGround() {
		return ground;
	}

	public int getLevel() {
		return level;
	}
	
	public double getLeftBound() {
		return leftBound;
	}
	
	public double getRightBound() {
		return rightBound;
	}

	public ArrayList<Entity> getProps() {
		return props;
	}

	public void setProps(ArrayList<Entity> props) {
		this.props = props;
	}

	public ArrayList<Collidable> getCollidable() {
		return collidable;
	}

	public void setCollidable(ArrayList<Collidable> collidable) {
		this.collidable = collidable;
	}

	public ArrayList<Interactable> getInteractable() {
		return interactable;
	}

	public void setInteractable(ArrayList<Interactable> interactable) {
		this.interactable = interactable;
	}

	public void setEnemy(ArrayList<Enemy> enemy) {
		this.enemy = enemy;
	}
	
}
