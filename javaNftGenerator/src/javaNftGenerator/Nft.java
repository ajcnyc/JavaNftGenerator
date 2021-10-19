package javaNftGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Nft {

	private HashMap<String, Component> components_; // maps component type to component

	public Nft(HashMap<String, Component> components) {
		components_ = components;
	}

	public boolean matches(Nft nft) {
		HashMap<String, Component> otherComponents = nft.getComponents();
		for (String key : components_.keySet()) {
			Component selfComponent = components_.get(key);
			Component otherComponent = otherComponents.get(key);
			if (selfComponent.getName().equals(otherComponent.getName()) == false) {
				return false;
			}
		}
		return true;
	}

//	public Image draw() {
//		ArrayList<Component> components = sortByLayers(components_.values());
//		// TODO: parameters for size
//		SimpleColor[][] finishedPixels = new SimpleColor[2350][2350];
//
//		for (int compNum = 0; compNum < components.size(); compNum++) {
//			// pixels = componentToPixels(components.get(compNum));
//			Component component = components.get(compNum);
//			Image img = new Image("file:" + component.getImageFile().getAbsolutePath());
//			// loop through each pixel position
//			for (int i = 0; i < finishedPixels.length; i++) {
//				for (int j = 0; j < finishedPixels[0].length; j++) {
//					//System.out.println(i + "," + j);
//					Color pixel = ImageTools.getPixelColor(img, i, j);
//					if (pixel.getOpacity() > 0.0) {
//						finishedPixels[i][j] = new SimpleColor(pixel.getRed(), pixel.getGreen(), pixel.getBlue(),
//								pixel.getOpacity());
//					}
//				}
//			}
//		}
//		Image finishedImage = ImageTools.makeImage(finishedPixels);
//		return finishedImage;
//	}

//	private Color[][] componentToPixels(Component component) {
//		Image image = new Image("file:" + component.getImageFile().getAbsolutePath());
//		Color[][] pix = ImageTools.getAllPixelColors(image);
//		ImageTools.get
//		image=null;
//		return pix;
//	}

	
	
	
	public Image draw() {
		long start = System.currentTimeMillis();
		ArrayList<Component> components = sortByLayers(components_.values());
		ArrayList<Color[][]> pixels = componentsToPixels(components);

		Color[][] finishedPixels = new Color[pixels.get(0).length][pixels.get(0)[0].length];

		// loop through each pixel position
		for (int i = 0; i < pixels.get(0).length; i++) {
			for (int j = 0; j < pixels.get(0)[0].length; j++) {
				for (int k = 0; k < pixels.size(); k++) { // loop through each image
					if (pixels.get(k)[i][j].getOpacity() > 0.0) {
						finishedPixels[i][j] = pixels.get(k)[i][j];
						break;
					}
				}
			}
		}
		Image finishedImage = ImageTools.makeImage(finishedPixels);
		System.out.println("Took: "+(System.currentTimeMillis()-start));
		return finishedImage;
	}

	private ArrayList<Color[][]> componentsToPixels(ArrayList<Component> components) {
		ArrayList<Color[][]> pixels = new ArrayList<Color[][]>();
		for (int i = 0; i < components.size(); i++) {
			Image image = new Image("file:" + components.get(i).getImageFile().getAbsolutePath());
			Color[][] pix = ImageTools.getAllPixelColors(image);
			pixels.add(pix);
		}
		return pixels;
	}

	private ArrayList<Component> sortByLayers(Collection<Component> components) {
		// Create ArrayList
		ArrayList<Component> componentList = new ArrayList<Component>(components);

		// Sort ArrayList by highest to lowest layer
		componentList.sort(new Comparator<Component>() {
			@Override
			public int compare(Component comp1, Component comp2) {
				if (comp1.getLayer() < comp2.getLayer()) {
					return 1;
				} else if (comp1.getLayer() > comp2.getLayer()) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		return componentList;
	}

	public HashMap<String, Component> getComponents() {
		return components_;
	}

}
