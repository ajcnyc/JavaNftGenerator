package javaNftGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

import javafx.scene.image.Image;

public class NftGenerator {

	private File componentsRoot_;
	private File outputFolder_;

	public NftGenerator(File componentsRoot, File outputFolder) {
		componentsRoot_ = componentsRoot;
		outputFolder_ = outputFolder;
	}

	public void generateUnique(int quantity) {

		// Number of combinations check
		int numPossibleCombos = countCombinations();
		if (quantity > numPossibleCombos) {
			throw new IllegalArgumentException("Cannot generate " + quantity + " NFTs from " + numPossibleCombos
					+ " possible combinations without repetitions");
		} else { // Confirmation
			System.out.println("Generate " + quantity + " NFTs from " + numPossibleCombos
					+ " possible combinations. If the number of NFTs is close to the number of combinations, "
					+ "this may take a long time. Proceed? (yes or no)");
			Scanner scanner = new Scanner(System.in);
			String choice = scanner.nextLine();
			scanner.close();
			if (choice.equalsIgnoreCase("yes") == false) {
				return;
			}
		}

		// Generate without repetitions
		ArrayList<ComponentMap> generatedCombos = new ArrayList<ComponentMap>();
		for (int i = 0; i < quantity; i++) {
			long start = System.currentTimeMillis();
			ComponentMap componentMap = null;
			for (; componentMap == null || generatedCombos.contains(componentMap);) {
				componentMap = generate();
			}
			Nft nft = new Nft(componentMap);
			Image nftImage = nft.draw();
			ImageTools.saveImage(nftImage, "png",
					new File(outputFolder_.getAbsolutePath() + File.separator + "#" + (i + 1)));
			System.out.println((i + 1) + " Took: " + (System.currentTimeMillis() - start));
			generatedCombos.add(componentMap);
		}
	}

	public void generateWithRepetitions(int quantity) {
		// Confirmation
		int numPossibleCombos = countCombinations();
		System.out.println("Generate " + quantity + " NFTs from " + numPossibleCombos
				+ " possible combinations. Proceed? (yes or no)");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.nextLine();
		scanner.close();
		if (choice.equalsIgnoreCase("yes") == false) {
			return;
		}

		for (int i = 0; i < quantity; i++) {
			long start = System.currentTimeMillis();
			ComponentMap componentMap = generate();
			Nft nft = new Nft(componentMap);
			Image nftImage = nft.draw();
			ImageTools.saveImage(nftImage, "png",
					new File(outputFolder_.getAbsolutePath() + File.separator + "#" + (i + 1)));
			System.out.println((i + 1) + " Took: " + (System.currentTimeMillis() - start));
		}
	}

	private ComponentMap generate() {
		ArrayList<ComponentFamily> families = allFilesToComponents();

		ComponentMap componentMap = new ComponentMap();
		for (int i = 0; i < families.size(); i++) {
			ComponentFamily family = families.get(i);
			componentMap.put(family.getComponentType(), family.select());
		}

		return componentMap;
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

	private int countCombinations() {
		int combos = 0;
		File[] familyFiles = componentsRoot_.listFiles();
		for (int i = 0; i < familyFiles.length; i++) {
			int numComponents = familyFiles[i].list().length;
			if (i == 0) {
				combos = combos + numComponents;
			} else {
				combos = combos * numComponents;
			}
		}
		return combos;
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
