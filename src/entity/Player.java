package entity;

import component.Collidable;
import component.Enemy;
import component.Entity;
import component.KeyStatus;
import component.PlayerStatus;
import component.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import logic.KeyHandler;
import logic.SceneManager;

public class Player extends Entity implements Collidable {
	
	//Stats
	private int hp;
	private int maxHp;
	private int atk;
	
	//Utility
	private static int atkable = 0;
	private static int immune = 0;
	protected static PlayerStatus face = PlayerStatus.RIGHT;
//	private static final AudioClip atkSound = new AudioClip(ClassLoader.getSystemResource("attackk.wav").toString());
	private static final double g = 0.35  ;
		
	// IDLE/RUN
	private PlayerStatus status;
	private PlayerStatus lastFrameStatus;
	private int direction;
	private static double moveSpeed = 7.0;
	
	// Jumping
	private PlayerStatus jumpStatus;
	private static double jumpSpeed;
	private static double initJumpSpeed = 10;
	private static final int maxJumpHeight = 300;
	private double currentJumpHeight;
	
	// Position
//	public static double x = 100;
//	public static double y = 500;
	public static boolean needResetPos = false;
	
	// Images
	private static final Sprite idle = new Sprite("sprite/character/player/idle.gif");
	private static final Sprite run = new Sprite("sprite/character/player/run.gif");
	private static final Sprite jump_up = new Sprite("sprite/character/player/jump_up.gif");
	private static final Sprite jump_down = new Sprite("sprite/character/player/jump_down.gif");
	private static final Sprite death = new Sprite("sprite/character/player/death.gif");
	private static final Sprite attack = new Sprite("sprite/character/player/attack.gif");
	
	private double prevx;
	

	public Player() {
		// TODO Auto-generated constructor stub
		
		super(150,SceneManager.getGround()-120,120,120);
		lastFrameStatus = PlayerStatus.IDLE;
		
		status = PlayerStatus.IDLE;
		jumpStatus = PlayerStatus.ONGROUND;
		direction = 0;
		currentJumpHeight = 0;
		
		hp =100;
		maxHp =100;
		atk = 10;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		prevx = getX();
		
		lastFrameStatus = status;
		status = PlayerStatus.RUN;
		if(needResetPos) {
			needResetPos = false;
		}
		if(atkable == 0 && jumpStatus.equals(PlayerStatus.ONGROUND) && KeyHandler.getInstance().getKeyStatus(83).equals(KeyStatus.DOWN)) {
			
			atkable += 81;
			//atkSound.play();
			attack();
			attack.loadImage(attack.getFilepath());
		}
		if(atkable > 41) {
			status = PlayerStatus.ATTACKING;
		}else {
			if(KeyHandler.getInstance().getKeyStatus(68).equals(KeyStatus.DOWN)) {
				if(getX() < SceneManager.getInstance().getRightBound()) increaseX(moveSpeed);;
				if(getX() > SceneManager.getInstance().getRightBound()- getW()) setX(SceneManager.getInstance().getRightBound() - getW());
				
				if(getX() >= SceneManager.getInstance().getLeftBound() + 640 - getW()) {
					double newOffSetX = SceneManager.getInstance().getOffsetX()+moveSpeed ;
					newOffSetX = (newOffSetX > SceneManager.getInstance().getRightBound() - 1280) ?  SceneManager.getInstance().getRightBound() - 1280 : newOffSetX; 
					SceneManager.getInstance().setOffsetX(newOffSetX);
				}
				
				direction = 1;
				face = PlayerStatus.RIGHT;
			}
			if(KeyHandler.getInstance().getKeyStatus(65).equals(KeyStatus.DOWN)) {
				if(getX() > SceneManager.getInstance().getLeftBound()) increaseX(-moveSpeed);;
				if(getX() < SceneManager.getInstance().getLeftBound()) setX(SceneManager.getInstance().getLeftBound());
					
				if(getX() <= SceneManager.getInstance().getRightBound() - 640 - getW()) {
					double newOffSetX = SceneManager.getInstance().getOffsetX()-moveSpeed;
					newOffSetX = (newOffSetX < SceneManager.getInstance().getLeftBound()) ?  SceneManager.getInstance().getLeftBound() : newOffSetX; 
					SceneManager.getInstance().setOffsetX(newOffSetX);
				}
				
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
			if(jumpSpeed == 0) {
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
			for(Enemy e:SceneManager.getInstance().getEnemy()) {
				if(e.collideWith(this)) {
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
		if(atkable > 40) {
			return attack;
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
				else super.draw(gc, getImage().getImage(),getX()+getW(),getY(),-getW(),getH());
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

	@Override
	public void checkCollide() {
		// TODO Auto-generated method stub
		
	}

	public void changeHp(int hp) {
		this.hp += hp;
		hp = (hp>maxHp) ? maxHp : (hp<0) ? 0 : hp;
	}
	
	public void changeAtk(int atk) {
		this.atk += atk;
	}
	
	public void changeJumpH(int h) {
		this.initJumpSpeed += h;
	}
	
	public void changemvsp(int sp) {
		this.moveSpeed += sp;
	}
}
