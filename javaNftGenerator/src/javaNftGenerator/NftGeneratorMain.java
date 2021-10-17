package javaNftGenerator;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;

public class NftGeneratorMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		// 1000 x 1000 pixels is strongly recommended for all your images, 
		// consistent image size is required
		NftGenerator generator = new NftGenerator(new File("components"), new File("output"));
		generator.generate();
		System.exit(0);
	}

}