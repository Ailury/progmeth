import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.HomeScreen;
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
		Scene scene = new Scene(new HomeScreen(stage));
		scene.setFill(Color.BLACK);
		stage.setScene(scene);
		stage.setTitle("Zenith chronicle");
		stage.setResizable(true);
		stage.show();
		
	}

}
