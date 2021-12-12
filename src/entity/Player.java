package entity;

import component.Enemy;
import component.Entity;
import component.KeyStatus;
import component.PlayerStatus;
import component.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import logic.KeyHandler;
import logic.SceneManager;

public class Player extends Entity {
	
	//Utility
	private static int atkable = 0;
	private static int immune = 0;
	protected static PlayerStatus face = PlayerStatus.RIGHT;
	private static final AudioClip atkSound = new AudioClip(ClassLoader.getSystemResource("attackk.wav").toString());
		
	// IDLE/RUN
	private PlayerStatus status;
	private int direction;
	private static final double moveSpeed = 7.0;
	
	// Jumping
	private PlayerStatus jumpStatus;
	private static final double jumpSpeed = 2.5;
	private static final int maxJumpHeight = 70;
	private double currentJumpHeight;
	
	// Position
//	public static double x = 100;
//	public static double y = 500;
	public static boolean needResetPos = false;
	
	// Images
	private static final Sprite idle = new Sprite("idle.gif");
	private static final Sprite run = new Sprite("run.gif");
	private static final Sprite jump = new Sprite("jump.gif");
	private static final Sprite death = new Sprite("death.gif");
	private static final Sprite atk = new Sprite("attack.gif");
		

	public Player() {
		// TODO Auto-generated constructor stub
		super(250,SceneManager.getGround()-120,120,120);
		status = PlayerStatus.IDLE;
		jumpStatus = PlayerStatus.ONGROUND;
		direction = 0;
		currentJumpHeight = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		status = PlayerStatus.RUN;
		if(needResetPos) {
			needResetPos = false;
		}
		if(atkable ==0 && currentJumpHeight==0 && KeyHandler.getInstance().getKeyStatus(83).equals(KeyStatus.DOWN)) {
			atkable += 101;
			atkSound.play();
			attack();
		}
		if(atkable > 93) {
			status = PlayerStatus.ATTACKING;
		}else {
			if(KeyHandler.getInstance().getKeyStatus(68).equals(KeyStatus.DOWN)) {
				increaseX(moveSpeed);;
				direction = 1;
				face = PlayerStatus.RIGHT;
			}
			if(KeyHandler.getInstance().getKeyStatus(65).equals(KeyStatus.DOWN)) {
				increaseX(-moveSpeed);;
				direction = -1;
				face = PlayerStatus.LEFT;
			}
			if(KeyHandler.getInstance().getKeyStatus(68).equals(KeyStatus.FREE) && KeyHandler.getInstance().getKeyStatus(65).equals(KeyStatus.FREE)) {
				direction = 0;
			}else if(KeyHandler.getInstance().getKeyStatus(68).equals(KeyStatus.DOWN) && KeyHandler.getInstance().getKeyStatus(65).equals(KeyStatus.DOWN)) {
				direction = 0;
			}
			if(direction == 0) {status = PlayerStatus.IDLE;}
			if(KeyHandler.getInstance().getKeyStatus(32).equals(KeyStatus.DOWN)) {
				if(jumpStatus.equals(PlayerStatus.ONGROUND)) {
					jumpStatus = PlayerStatus.GOINGUP;
				}
			}
		}
		if(jumpStatus.equals(PlayerStatus.GOINGUP)) {
			currentJumpHeight += jumpSpeed;
			increaseY(-jumpSpeed);
			if(currentJumpHeight == maxJumpHeight) {
				jumpStatus = PlayerStatus.FALLING;
			}
		}
		if(jumpStatus.equals(PlayerStatus.FALLING)) {
			currentJumpHeight -= jumpSpeed;
			increaseY(jumpSpeed);
			if(currentJumpHeight <= 0) {
				jumpStatus = PlayerStatus.ONGROUND;
			}
		}
		if(immune ==0) {
			for(Entity e:SceneManager.getInstance().getEntity()) {
				if((e instanceof Enemy) && e.collideWith(this)) {
					takeDamage(10);
					immune += 201;
				}
			}
		}
		atkable = (atkable == 0) ? atkable : atkable - 1;
		immune = (immune == 0) ? immune : immune - 1;
	}

	@Override
	public Sprite getImage() {
		// TODO Auto-generated method stub
		if(status == PlayerStatus.ATTACKING) {
			return atk;
		}
		if(!jumpStatus.equals(PlayerStatus.ONGROUND)){
			return jump;
		}
		if(status.equals(PlayerStatus.RUN)) {
			return run;
		}
		return idle;
	}

	@Override
	public void draw(GraphicsContext gc,boolean f) {
		// TODO Auto-generated method stub
		if(immune%2 == 0) {
			if(status != PlayerStatus.ATTACKING && status != PlayerStatus.DIE) {
//				if(direction != -1) super.draw(gc, false);
				if(direction != -1)super.draw(gc, getImage().getImage(), getX(), getY(), getW(), getH());
				else super.draw(gc, true);
			}else {
				if(direction != -1)super.draw(gc, getImage().getImage(), getX(), getY(), getW(), getH());
				else super.draw(gc,getImage().getImage(), getX()+getW(),getY(),-getW(),getH());
			}
		}
	}

	private void attack() {
		
	}
	
	private void takeDamage(int x) {
		
	}
	
}
