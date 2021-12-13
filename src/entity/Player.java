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
	private static final double g = 0.35  ;
		
	// IDLE/RUN
	private PlayerStatus status;
	private PlayerStatus lastFrameStatus;
	private int direction;
	private static final double moveSpeed = 7.0;
	
	// Jumping
	private PlayerStatus jumpStatus;
	private static double jumpSpeed;
	private static final double initJumpSpeed = 10;
	private static final int maxJumpHeight = 300;
	private double currentJumpHeight;
	
	// Position
//	public static double x = 100;
//	public static double y = 500;
	public static boolean needResetPos = false;
	
	// Images
	private static final Sprite idle = new Sprite("idle.gif");
	private static final Sprite run = new Sprite("run.gif");
	private static final Sprite jump_up = new Sprite("jump_up.gif");
	private static final Sprite jump_down = new Sprite("jump_down.gif");
	private static final Sprite death = new Sprite("death.gif");
	private static final Sprite atk = new Sprite("attack.gif");
	
	
	private double prevY ;

	public Player() {
		// TODO Auto-generated constructor stub
		
		super(250,SceneManager.getGround()-120,120,120);
		lastFrameStatus = PlayerStatus.IDLE;
		prevY = SceneManager.getGround();
		status = PlayerStatus.IDLE;
		jumpStatus = PlayerStatus.ONGROUND;
		direction = 0;
		currentJumpHeight = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		lastFrameStatus = status;
		status = PlayerStatus.RUN;
		if(needResetPos) {
			needResetPos = false;
		}
		if(atkable == 0 && jumpStatus.equals(PlayerStatus.ONGROUND) && KeyHandler.getInstance().getKeyStatus(83).equals(KeyStatus.DOWN)) {
			
			atkable += 81;
			atkSound.play();
			attack();
			atk.loadImage(atk.getFilepath());
		}
		if(atkable > 41) {
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
					jumpSpeed = initJumpSpeed;
					jumpStatus = PlayerStatus.GOINGUP;
				}
			}
		}
		if(jumpStatus.equals(PlayerStatus.GOINGUP)) {
			currentJumpHeight += jumpSpeed;
			increaseY(-jumpSpeed);
			jumpSpeed = (jumpSpeed > 0) ? jumpSpeed - g : 0;
			if(currentJumpHeight >= maxJumpHeight || jumpSpeed == 0) {
				jumpStatus = PlayerStatus.FALLING;
			}
		}
		if(jumpStatus.equals(PlayerStatus.FALLING)) {
			currentJumpHeight -= jumpSpeed;
			if(currentJumpHeight <= 0) jumpSpeed -= -currentJumpHeight;
			increaseY(jumpSpeed);
			if(getY() > 530) setY(SceneManager.getGround() - getH());
			jumpSpeed = jumpSpeed + g;
			if(currentJumpHeight <= 0) {
				currentJumpHeight = 0;
				jumpStatus = PlayerStatus.ONGROUND;
				jumpSpeed = initJumpSpeed;
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
		if(getY() != prevY)System.out.println(getY());
		prevY = getY();
		atkable = (atkable == 0) ? atkable : atkable - 1;
		immune = (immune == 0) ? immune : immune - 1;
	}

	@Override
	public Sprite getImage() {
		// TODO Auto-generated method stub
		if(atkable > 40) {
			return atk;
		}
		if(jumpStatus.equals(PlayerStatus.GOINGUP)){
			return jump_up;
		}
		if(jumpStatus.equals(PlayerStatus.FALLING)){
			return jump_down;
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
			if(!lastFrameStatus.equals(status) && atkable < 41) getImage().loadImage(getImage().getFilepath());
			if(atkable < 41 && status != PlayerStatus.DIE) {
//				if(direction != -1) super.draw(gc, false);
				if(!face.equals(PlayerStatus.LEFT))super.draw(gc, getImage().getImage(), getX(), getY(), getW(), getH());
				else super.draw(gc, true);
			}else {
				if(!face.equals(PlayerStatus.LEFT))super.draw(gc, getImage().getImage(), getX()-getW()/3, getY(), (getW()*5)/3, getH());
				else super.draw(gc,getImage().getImage(), getX()+getW()*4/3,getY(),-getW()*5/3,getH());
			}
		}
	}

	private void attack() {
		
	}
	
	private void takeDamage(int x) {
		
	}
	
}
