package javaNftGenerator;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.image.Image;

public class ComponentFamily {

	private String componentType_;
	private int layer_;
	private HashSet<Component> components_;

	private ArrayList<Component> choices_; // internal only. Components appear 
										   //the number of times of their rarity

	public ComponentFamily(File familyRoot) {
		// Get componentType and layer from directory name
		String[] familyFileNameParts = familyRoot.getName().split("_");

		// Using .length instead of [0] and [1] to allow for previous underscores in
		// name
		String componentType = familyFileNameParts[familyFileNameParts.length - 2];
		int layer = Integer.parseInt(familyFileNameParts[familyFileNameParts.length - 1]);

		// The NFT images
		HashSet<Component> components = new HashSet<Component>();
		File[] imageFiles = familyRoot.listFiles();
		for (int i = 0; i < imageFiles.length; i++) {
			String name = getName(imageFiles[i].getName());
			int rarity = getRarity(imageFiles[i].getName());
			components.add(new Component(name, imageFiles[i], layer, rarity));
		}

		componentType_ = componentType;
		layer_ = layer;
		components_ = components;
		choices_ = generateChoices();
	}

	private String getName(String imageFileName) {
		String name = imageFileName.replace(".png", "");
		String[] nameParts = name.split("_");
		return nameParts[nameParts.length - 2];
	}

	private int getRarity(String imageFileName) {
		String name = imageFileName.replace(".png", "");
		String[] nameParts = name.split("_");
		int rarity = Integer.parseInt(nameParts[nameParts.length - 1]);
		return rarity;
	}

	public ComponentFamily(String componentType, int layer, HashSet<Component> components) {
		componentType_ = componentType;
		layer_ = layer;
		components_ = components;
		choices_ = generateChoices();
	}

	private ArrayList<Component> generateChoices() {
		ArrayList<Component> choices = new ArrayList<Component>();

		// For each component
		for (Component component : components_) {
			// Add it to choices the number of times of its rarity
			int rarity = component.getRarity();
			for (int i = 0; i < rarity; i++) {
				choices.add(component);
			}
		}

		return choices;
	}

	public Component select() {
		// Randomly select a component name from choices
		SecureRandom random = new SecureRandom();
		int index = random.nextInt(choices_.size());
		return choices_.get(index); 
	}

	public String getComponentType() {
		return componentType_;
	}

	public int getLayer() {
		return layer_;
	}

	public HashSet<Component> getComponents() {
		return components_;
	}

}
