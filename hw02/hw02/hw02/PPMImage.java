package hw02;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PPMImage {	
	public static void main(String[] args) {
		PPMImage p = new PPMImage("p4.ppm");
//		p.grayscale();
		p.negative();
//		p.sepia();
	}
	
	
	/**This {@code String} is  magic number. */
	private String magicNumber;
	
	/**This value is the width of the picture. */
	private int width;
	
	/**This value is the height of the picture. */
	private int height;
	
	/** This value is maximum color values, which is 3. */
	private int maxColorValue;

	/** An instance variable for an array that stores data of the image byte
	 * by byte. */
	private char[] raster;
	
	/** These are the accessors and mutators.
	 * 
	 * @return The String is the magic number of the .ppm file.*/
	public String getMagicNumber() {
		return magicNumber;
	}
	/**@return The int is the width of the image.*/
	public int getWidth() {
		return width;
	}
	/**@return The int is the height of the image.*/
	public int getHeight() {
		return height;
	}
	
	public int getMaxColorValue() {
		return maxColorValue;
	}
 
	/** Initializes a {@code PPMImage } object and it invokes the readImage
	 * method. 
	 * 
	 * @param imageFileName 	is the name of the PPM image. */
	public PPMImage(String imageFileName) {
		readImage(imageFileName);
	}

	/** @param outputImageFileName	 name of the file to copy the data to. */
	public void writeImage(String outputImageFileName) {
		try(
				/** It creates an BurreredOutputStream object, which is where 
				 * we write new data to and takes the path of the new file. */
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
						String.format("src/files/%s", outputImageFileName)
						
						));
				)
		{
			/** for loop to read each character from a string and writes it to
			 * the BufferedOutputSteam object. */
			for (int index = 0; index < magicNumber.length(); index++) {
				bos.write(magicNumber.charAt(index));
			}
		
			bos.write('\n');
			
			/**This {@code String} is converts the width,an int type, to a String.*/
			String widthString = String.valueOf(width);
			
			/** Iterates through the string at each index and writes each
			 * character to the object created above. */
			for (int index = 0; index < widthString.length(); index++) {
				bos.write(widthString.charAt(index));
			}
			
			bos.write(' ');
			
			
			/**This {@code String} is converts the height of the .ppm image 
			 * to a String.*/
			String heightString = String.valueOf(height);
			
			
			/** Iterate through the string at each index and write each
			 * character to the object created above. */
			for (int index = 0; index < heightString.length(); index++) {
				bos.write(heightString.charAt(index));
			}
			
			bos.write('\n');
			
			/** Iterate through the string maxValue at each index and write each
			 * character to the object created above. */
			String maxValue= String.valueOf(this.maxColorValue);
			for (int index = 0; index < maxValue.length(); index++) {
				bos.write(maxValue.charAt(index));
			}
			
			bos.write('\n');
			
			int index =  maxValue.length()+widthString.length()+
					heightString.length()+3+magicNumber.length();
			
			/** Repeat do while loop until it reaches the end of 1D array. */			
			int data = 0;
			do {
				data = raster[index];
				bos.write(data);
				index++;
			} while (index<raster.length);
			System.out.println("From writing. Finished Writing to the file.");
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	/** @param imageFileName 	is the name of the PPM image. */
	private void readImage(String imageFileName) {
		try (
				/** This {@code Object} is an object where data is coming from.*/
				
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
						String.format("src/files/%s", imageFileName)));
				)
		
		{		
			String magicNumber = Character.toString((char) bis.read()).
					concat(Character.toString((char) bis.read()));
				
				
			
			//skipping to the next line in the file.
			char lineFeed1 = (char)bis.read();
			
			// finding the height of the .ppm file.
			String widthString = "";
			int loops = 0;
			while((loops<=3)) {
				char holder = (char)bis.read();
				if(holder!='\n') {
					widthString +=Character.toString(holder);	
				}
				else {
					break;	
				}
				loops++;	
			}

			// Skipping a space character.
			char space1  = (char) bis.read();
			
			// finding the height of the .ppm file.
			String heightString = "";
			int loops2 = 0;
			while ((loops2 <= 3)) {
				char holder2 = (char) bis.read();
				if (holder2 != '\n') {
					heightString += Character.toString(holder2);
				} else {
					break;
				}
				loops++;
			}
			
			/** Setting the data fields to values from this method. */	
			this.magicNumber = magicNumber;
			this.width = Integer.valueOf(widthString);
			this.height = Integer.valueOf(heightString);
			this.maxColorValue = 255;
			
			/** Additional length is for the chars from the image header. */
			int additionalLength = magicNumber.length()+1+widthString.length()
			+1+heightString.length();
			
			raster = new char[Integer.valueOf(widthString)*
			                  Integer.valueOf(heightString)*
			                  3+(additionalLength+5)];
			
			/** Adding chars to the raster array. */
			int index = 0;
			for(int i=0;i<magicNumber.length();i++) {
				raster[index] = magicNumber.charAt(i);
				index++;
			}
			
			raster[index] = lineFeed1;
			index++;
			
			/** Go through each character of the image width and add them to 
			 * raster array. */
			for(int i=0;i<widthString.length();i++) {
				raster[index] = widthString.charAt(i);
				index++;
			}
			
			raster[index] = space1;
			index++;
			
			/** Add each character of the image height to the raster array. */
			for(int i=0;i<heightString.length();i++) {
				raster[index] = heightString.charAt(i);
				index++;
			}
			
			/** Reading the rest of the file. */
			int data = 0;
			do {
				data = bis.read();
				raster[index] = (char) data;				
				index++;
			} while(data != -1);
			System.out.println("From reading. Finished reading the file!");
		}
		catch (EOFException e) {
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** This method changes the data of the image pixels using a formula. */
	public void grayscale() {
		try(
				BufferedOutputStream bos = new BufferedOutputStream(new
						FileOutputStream("src/files/grayScale_COPY.ppm"));)
		{
			/** for loop to separate magicNumber String into its character 
			 * components and write each one to the file. */
			for (int index = 0; index < magicNumber.length(); index++) {
				bos.write(magicNumber.charAt(index));
			}
			
			bos.write('\n');

			/** Convert the width to a {code String} */
			String widthString = String.valueOf(width);
			
			/** iterate through each index of the string & write it to the
			 * file*/
			for (int index = 0; index < widthString.length(); index++) {
				bos.write(widthString.charAt(index));
			}
			
			bos.write(' ');
			
			String heightString = String.valueOf(height);
			
			/** iterate through each index of the string & write it to the
			 * file*/
			for (int index = 0; index < heightString.length(); index++) {
				bos.write(heightString.charAt(index));
			}
			
			bos.write('\n');
			
			/** This code {code String} takes the integer value of the String.*/
			String maxValue= String.valueOf(this.maxColorValue);
			/** Write each character from this String and write it the file.*/
			for (int index = 0; index < maxValue.length(); index++) {
				bos.write(maxValue.charAt(index));
			}
			
			bos.write('\n');
			
			/** This variable stores the continued index of the data to write
			 * to the file.*/
			int index =  maxValue.length()+widthString.length()+
					heightString.length()+3+magicNumber.length();
			
			/** Repeat the loop until reaches the end of the 1D raster array. */
			while(index<raster.length) {
				if(index>=raster.length-2) {
					bos.write(raster[index-2]);	
				}
				
				else {
					/** Use variables to store red, green, and blue pixels. */
					int R = raster[index++];
					int G = raster[index++];
					int B = raster[index];

					/** Use the formula to convert a color image to gray
					 * scale.*/
					int rPrime = (int) (R * (0.299) + (G * .587) + (B * .114));
					int gPrime = (int) (R * (0.299) + (G * .587) + (B * .114));
					int bPrime = (int) (R * (0.299) + (G * .587) + (B * .114));

					/** Write each pixel data to the file after they have been
					 * manipulated above. */
					bos.write(rPrime);
					bos.write(gPrime);
					bos.write(bPrime);
				}
				index++;
			}
			System.out.println("Reached end of grayScale().");
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	
	/** This method changes the original image color to sepia. */
	public void sepia() {
		try (
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream("src/files/sepia_COPY.ppm"));) {
			
			
			
			/** Write each character of the magic number to the image header. */
			for (int index = 0; index < magicNumber.length(); index++) {
				bos.write(magicNumber.charAt(index));
			}

			bos.write('\n');

			
			
			/** Convert the width to a String type to access its character
			 * indexes. */
			String widthString = String.valueOf(width);
			
			/** Write each character from the widthString to the file.*/
			for (int index = 0; index < widthString.length(); index++) {
				bos.write(widthString.charAt(index));
			}

			bos.write(' ');

			/** The {code String} heightString converts an int type to String
			 * type. */
			String heightString = String.valueOf(height);
			
			/** Write character by character of the heightString to the file. */
			for (int index = 0; index < heightString.length(); index++) {
				bos.write(heightString.charAt(index));
			}

			bos.write('\n');

			/** Write each character of the maxColorValue to the file. */
			String maxValue = String.valueOf(this.maxColorValue);
			for (int index = 0; index < maxValue.length(); index++) {
				bos.write(maxValue.charAt(index));
			}

			bos.write('\n');

			/** This variable stores the continued index of the data to write
			 * to the file.*/
			int index = maxValue.length() + widthString.length() +
					heightString.length() + 3 + magicNumber.length();

			/** Repeat the loop until reaches the end of the 1D raster array. */
			while (index < raster.length) {
				
				/** Use this condition close to the end of 1D array to avoid
				 * out of array bound exception.*/
				if (index >= raster.length -2) {
					bos.write(raster[index - 2]);
				}
				
				
				/** This condition holds three variables that point to three 
				 * different characters a pixel is made of; red green and,
				 * blue. */
				else {
					int R = raster[index++];
					int G = raster[index++];
					int B = raster[index];

					/** Compute the values of the new pixel using the given
					 * formula. */
					int rPrime = (int) (R * (0.393) + (G * .769) + (B * .189));
					int gPrime = (int) (R * (0.349) + (G * .686) + (B * .168));
					int bPrime = (int) (R * (0.272) + (G * .534) + (B * .131));

					
					/** Write prime values to the file. */
					bos.write(rPrime);
					bos.write(gPrime);
					bos.write(bPrime);
				}
				index++;
			}
			System.out.println("Reached end of sepia().");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	
	/** This method converts an image in color to negative, black and white. */
	public void negative() {
		try(
				BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream("src/files/negative_COPY.ppm"));)
		{
			
//			/** Write each char from the magic number to the file. */
//			for (int index = 0; index < magicNumber.length(); index++) {
//				bos.write(magicNumber.charAt(index));
//			}
//			
//			
//			bos.write('\n');
//			
//			/** Convert width from int type to String type. */
//			String widthString = String.valueOf(width);
//			
//			/** Access the indices of each char in te height String and write
//			 * it the file. */
//			for (int index = 0; index < widthString.length(); index++) {
//				bos.write(widthString.charAt(index));
//			}
//			
//			
//			bos.write(' ');
//			
//			
//			/** Convert height from int type to String type. */
//			String heightString = String.valueOf(height);
//			
//			/** Iterate through each char in this String and write it the the
//			 * file as the header. */
//			for (int index = 0; index < heightString.length(); index++) {
//				bos.write(heightString.charAt(index));
//			}
//			
//			bos.write('\n');
//			
//			/** Convert maxColorValue from int type to String type. */
//			String maxValue= String.valueOf(this.maxColorValue);
//			
//			/** Update the file by writing each character of the maxColorValue
//			 * to the file. */
//			for (int index = 0; index < maxValue.length(); index++) {
//				bos.write(maxValue.charAt(index));
//			}
//			
//			bos.write('\n');
			
			/** Additional length of the raster array to store the header
			 * characters. */
//			int index =  maxValue.length()+widthString.length()+
//					heightString.length()+3+magicNumber.length();
			
			
			
			
			int index =  String.valueOf(getMaxColorValue()).length()+String.valueOf(width).length()+
					String.valueOf(width).length()+String.valueOf(height).length()+3+String.valueOf(magicNumber).length();
			
			/** Repeat do while loop until it reaches the end of 1D array. */			
			while(index<raster.length-3) {
				
				/** Use this condition if it is near the end of the array to
				 * avoid array out of bound exception. */
				if (index >= raster.length - 2) {
					raster[index] = raster[255-(raster[index - 2])];
//					bos.write(255-(raster[index - 2]));
				}
				else {
					int index1 = index+1;
					int index2 = index+1;
					int index3 = index+1;
					/** Holding three character from the raster array to 
					 * reference them later. */
					int R = raster[index1];
					int G = raster[index2];
					int B = raster[index3];

					/** Converting the color pixels to negative pixel values. */
					int rPrime = 255 - R;
					int gPrime = 255 - G;
					int bPrime = 255 - B;

					/** update the data from the file by write prime values to it. */
					raster[index1] = raster[rPrime];
					raster[index2] = raster[gPrime];
					raster[index3] = raster[bPrime];
//					bos.write(rPrime);
//					bos.write(gPrime);
//					bos.write(bPrime);
				}
//				index++;
				
			}
			System.out.println("Reached end of negative().");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
