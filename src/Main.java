import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.KeyHandler;
import logic.SceneManager;

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
		scene.setFill(Color.BLACK);
		stage.setScene(scene);
		stage.setTitle("Zenith chronicle");
		SceneManager.getInstance().gameStart();
		root.getChildren().add(KeyHandler.getInstance());
		KeyHandler.getInstance().requestFocus();
		stage.setResizable(false);
		stage.show();
		
		AnimationTimer animation = new AnimationTimer(){
			public void handle(long now) {
				KeyHandler.getInstance().update();
				SceneManager.getInstance().update();
			}
		};
		animation.start();
	}

}
