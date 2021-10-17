package javaNftGenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * ImageTools provides a number of tools for analyzing, creating, and
 * manipulating images.
 * 
 * @author Alex Cohen
 */
public class ImageTools {

	/**
	 * Gets the color of the pixel at the specified x and y coordinates on the
	 * specified image
	 * 
	 * @param image
	 *          The image to use
	 * @param x
	 *          The x coordinate of the desired pixel
	 * @param y
	 *          The y coordinate of the desired pixel
	 * @return The color of the pixel
	 */
	public static Color getPixelColor ( Image image, int x, int y ) {
		PixelReader reader = image.getPixelReader();
		return reader.getColor(y,x);
	}

	/**
	 * Gets all the colors of the pixels in the specified image
	 * 
	 * @param image
	 *          The image to use
	 * @return A 2D array of all the pixel colors in the region
	 */
	public static Color[][] getAllPixelColors ( Image image ) {
		Color[][] colors =
		    new Color[(int) image.getHeight()][(int) image.getWidth()];
		for ( int i = 0 ; i < image.getHeight() ; i++ ) {
			for ( int j = 0 ; j < image.getWidth() ; j++ ) {
				colors[i][j] = getPixelColor(image,i,j);
			}
		}
		return colors;
	}

	/**
	 * Constructs an image from the given array of pixel colors
	 * 
	 * @param pixels
	 *          The array of Colors representing all the pixels
	 * @return An image created from the given array
	 */
	public static Image makeImage ( Color[][] pixels ) {
		WritableImage wi = new WritableImage(pixels[0].length,pixels.length);
		PixelWriter pw = wi.getPixelWriter();
		for ( int i = 0 ; i < pixels.length ; i++ ) {
			for ( int j = 0 ; j < pixels[i].length ; j++ ) {
				pw.setColor(j,i,pixels[i][j]);
			}
		}
		Image image = (Image) wi;
		return image;
	}

	/**
	 * Saves the provided image to the given save file with the given file format
	 * 
	 * @param image
	 *          The image to save
	 * @param fileFormat
	 *          The file format to save as (e.g. png NOT .png)
	 * @param saveFile
	 *          The file that the image will be saved to
	 */
	public static void saveImage ( Image image, String fileFormat,
	                               File saveFile ) {
		// Using the awtImage fixes alpha issues, to allow .JPG files to be saved
		BufferedImage awtImage =
		    new BufferedImage((int) image.getWidth(),(int) image.getHeight(),
		                      BufferedImage.TYPE_INT_RGB);
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image,awtImage);

		// If the file format is not included at the end of the saveFile's name, add
		// the file format to the end of the saveFile's name
		if ( saveFile.getName().endsWith(fileFormat) == false ) {
			String newPath = saveFile.getAbsolutePath();
			if ( fileFormat.charAt(0) != '.' ) {
				newPath = newPath + ".";
			}
			newPath = newPath + fileFormat;

			saveFile = new File(newPath);
		}
		try {
			// Save image
			ImageIO.write(bufferedImage,fileFormat,saveFile);
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
