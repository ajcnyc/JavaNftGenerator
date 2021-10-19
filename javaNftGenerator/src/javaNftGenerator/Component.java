package javaNftGenerator;

import java.io.File;

import javafx.scene.image.Image;

public class Component {

	private String name_;
	private File imageFile_;
	private int layer_;
	private int rarity_;

	public Component(String name, File imageFile, int layer, int rarity) {
		name_ = name;
		imageFile_ = imageFile;
		layer_ = layer;
		rarity_ = rarity;
	}

	public String getName() {
		return name_;
	}

	public File getImageFile() {
		return imageFile_;
	}

	public int getLayer() {
		return layer_;
	}
	
	public int getRarity() {
		return rarity_;
	}

}
