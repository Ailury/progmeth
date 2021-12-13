package logic;

import java.io.Serializable;
import java.util.ArrayList;

import component.Entity;
import entity.Background;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SceneManager extends Canvas implements Serializable {

	private static SceneManager instance = null;
	private double offsetX;
	private double offsetY;
	
	private ArrayList<Entity> entitys;
	private Player player;
	
	private static final int ground = 650;
	private int level;
	
	private SceneManager() {
		// TODO Auto-generated constructor stub
		super(1280, 720);
		
		entitys = new ArrayList<Entity>();
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
		for(Entity e : entitys) {
			e.update();
		}
		player.update();
		draw();
	}
	
	public void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.RED);
		for(Entity e : entitys) {
			e.draw(gc,false);
		}
		player.draw(gc,false);
	}
	
	public void startlLevel() {
		
	}
	
	public void gameStart() {
		entitys.add(new Background());
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

	public ArrayList<Entity> getEntity() {
		return entitys;
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
	
}
