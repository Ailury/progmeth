import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.KeyHandler;
import logic.SceneManager;
import java.time.Instant;


public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		scene.setFill(Color.PINK);
		stage.setScene(scene);
		stage.setTitle("Zenith chronicle");
		SceneManager.getInstance().gameStart();
		root.getChildren().add(KeyHandler.getInstance());
		root.getChildren().add(SceneManager.getInstance());
		KeyHandler.getInstance().requestFocus();
		stage.setResizable(true);
		stage.show();
		
		AnimationTimer animation = new AnimationTimer(){
			public void handle(long now){
				 new Thread(() -> {
					KeyHandler.getInstance().update();
					SceneManager.getInstance().update(); } ).start();
					try {Thread.sleep(5);
					} catch(Exception e) {} 
			
					//KeyHandler.getInstance().update();
					//SceneManager.getInstance().update();
			}
		};
		animation.start();
	}

}
