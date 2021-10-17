package javaNftGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javafx.scene.image.Image;

public class NftGenerator {

	private File componentsRoot_;
	private File outputFolder_;

	public NftGenerator(File componentsRoot, File outputFolder) {
		componentsRoot_ = componentsRoot; 
		outputFolder_ = outputFolder;
	}

	public void generate(int quantity) {
		
		// keep ImageTools.saveImage(nftImage, ".png", new File(outputFolder_.getAbsolutePath()+File.separator+num));
	}
	
	public Image generate() {
		ArrayList<ComponentFamily> families = allFilesToComponents();
		
		HashMap<String, Component> componentMap = new HashMap<String, Component>();
		for (int i = 0; i < families.size(); i++) { 
			ComponentFamily family = families.get(i);
			componentMap.put(family.getComponentType(), family.select());
		}
		Nft nft = new Nft(componentMap);
		for (String key: componentMap.keySet() ) { 
			Component component = componentMap.get(key);
			System.out.println(key+": "+component.getName());
		}
		Image nftImage = nft.draw();
		// Temp vvv
		System.out.println("nftImage: "+nftImage);
		ImageTools.saveImage(nftImage, "png", new File(outputFolder_.getAbsolutePath()+File.separator+"NFT"));
		return nftImage;
	}

	private ArrayList<ComponentFamily> allFilesToComponents() {
		ArrayList<ComponentFamily> componentFamilies = new ArrayList<ComponentFamily>();

		File[] familyFiles = componentsRoot_.listFiles();
		for (int i = 0; i < familyFiles.length; i++) {
			ComponentFamily family = new ComponentFamily(familyFiles[i]);
			componentFamilies.add(family);
		}

		return sort(componentFamilies);
	}

	private ArrayList<ComponentFamily> sort(ArrayList<ComponentFamily> componentFamilies) {
		// sort lowest to highest layer
		componentFamilies.sort(new Comparator<ComponentFamily>() {
			public int compare(ComponentFamily fam1, ComponentFamily fam2) {
				if (fam1.getLayer() < fam2.getLayer()) {
					return -1;
				} else if (fam1.getLayer() > fam2.getLayer()) {
					return 1;
				} else {
					return 0;
				}
			}
		});

		return componentFamilies;
	}

}
