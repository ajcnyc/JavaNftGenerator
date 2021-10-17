package javaNftGenerator;

import javafx.scene.image.Image;

public class Component {

	private String name_;
	private Image image_;
	private int layer_;
	private int rarity_;

	public Component(String name, Image image, int layer, int rarity) {
		name_ = name;
		image_ = image;
		layer_ = layer;
		rarity_ = rarity;
	}

	public String getName() {
		return name_;
	}

	public Image getImage() {
		return image_;
	}

	public int getLayer() {
		return layer_;
	}
	
	public int getRarity() {
		return rarity_;
	}

}
